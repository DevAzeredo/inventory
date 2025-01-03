package dev.azeredo.presentation.productlist

import Product
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.mohamedrejeb.calf.picker.toImageBitmap
import dev.azeredo.presentation.AppIcons
import dev.azeredo.presentation.addproduct.AddProductScreen
import dev.azeredo.presentation.inbound.InboundScreen
import dev.azeredo.presentation.outbound.OutboundScreen
import dev.azeredo.presentation.stockmovement.StockMovementScreen
import kotlinx.coroutines.flow.Flow
import org.koin.compose.viewmodel.koinViewModel

class ProductListScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<ProductListViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        Scaffold(topBar = {
            SearchTopBar(
                onSearchQueryChanged = { viewModel.onSearchQueryChanged(it) },
                uiState.searchQuery
            )
        },
            floatingActionButton = { ProductFabMenu(navigator) },
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxSize().padding(top = 100.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                ProductListContent(
                    productList = uiState.productListFiltered, navigator = navigator, uiState = uiState
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(onSearchQueryChanged: (String) -> Unit, searchQuery: String) {
    TopAppBar(
        title = {
            TextField(value = searchQuery,
                onValueChange = { query ->
                    onSearchQueryChanged(query)
                },
                placeholder = { Text("Search products") },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            )
        },
    )
}

@Composable
fun ProductFabMenu(navigator: Navigator) {
    var isFabMenuExpanded by remember { mutableStateOf(false) }

    Box {
        FloatingActionButton(onClick = { isFabMenuExpanded = !isFabMenuExpanded },
            content = { Icon(Icons.Default.Menu, contentDescription = "Options") })

        DropdownMenu(expanded = isFabMenuExpanded,
            onDismissRequest = { isFabMenuExpanded = false }) {
            DropdownMenuItem(text = { Text("Add Product") }, onClick = {
                isFabMenuExpanded = false
                navigator.push(AddProductScreen())
            }, leadingIcon = { Icon(Icons.Default.Add, contentDescription = "Add Product") })
            DropdownMenuItem(text = { Text("Inbound") }, onClick = {
                isFabMenuExpanded = false
                navigator.push(InboundScreen())
            }, leadingIcon = {
                Icon(
                    AppIcons.Outbound,
                    contentDescription = "Inbound",
                    modifier = Modifier.rotate(180f)
                )
            })
            DropdownMenuItem(text = { Text("Outbound") }, onClick = {
                isFabMenuExpanded = false
                navigator.push(OutboundScreen())
            }, leadingIcon = { Icon(AppIcons.Outbound, contentDescription = "Outbound") })

            DropdownMenuItem(text = { Text("Reports") }, onClick = {
                navigator.push(StockMovementScreen())
                isFabMenuExpanded = false
            }, leadingIcon = { Icon(Icons.Outlined.Info, contentDescription = "Reports") })
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductListContent(
    productList: Flow<List<Product>>,
    modifier: Modifier = Modifier,
    navigator: Navigator,
    uiState: ProductListViewModel.ProductListUiState
) {
    val products by productList.collectAsState(initial = emptyList())

    FlowRow(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        products.forEach { product ->
            Box(modifier = Modifier.width(300.dp).padding(8.dp)) {
                ProductItem(
                    product = product,
                    onClick = { navigator.push(AddProductScreen(product)) },
                    uiState.productImages[product.id] ?: ByteArray(0)
                )
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onClick: () -> Unit, image: ByteArray) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable(onClick = onClick),
    ) {
        Column(modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)) {

            if (image.isNotEmpty()) {
                Image(
                    bitmap = image.toImageBitmap(),
                    contentDescription = "Product photo",
                    modifier = Modifier.padding(16.dp).width(50.dp).height(50.dp),
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.Face,
                    contentDescription = "Product photo",
                    modifier = Modifier.padding(16.dp).width(50.dp).height(50.dp),
                )
            }
            Text(text = product.name, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Category: ${product.category.description}")
            Text(text = "Quantity: ${product.quantity}")
        }
    }
}