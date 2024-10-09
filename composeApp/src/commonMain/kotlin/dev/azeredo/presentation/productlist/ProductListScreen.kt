package dev.azeredo.presentation.productlist

import Product
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.Flow
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProductListScreen(navController: NavController) {
    val viewModel = koinViewModel<ProductListViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { SearchTopBar(viewModel, navController) },
        floatingActionButton = { ProductFabMenu(navController) },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            ProductListContent(
                productList = uiState.productListFiltered,
                onRemoveProduct = { product -> viewModel.removeProduct(product) },
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(viewModel: ProductListViewModel, navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }

    TopAppBar(
        title = {
            TextField(
                value = searchQuery, onValueChange = { query ->
                    searchQuery = query
                    viewModel.onSearchQueryChanged(query)
                },
                placeholder = { Text("Search products") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFabMenu(navController: NavController) {
    var isFabMenuExpanded by remember { mutableStateOf(false) }

    Box {
        FloatingActionButton(
            onClick = { isFabMenuExpanded = !isFabMenuExpanded },
            content = { Icon(Icons.Default.Add, contentDescription = "Options") }
        )

        DropdownMenu(
            expanded = isFabMenuExpanded,
            onDismissRequest = { isFabMenuExpanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Add Product") },
                onClick = {
                    isFabMenuExpanded = false
                    navController.navigate("AddProductScreen")

                },
                leadingIcon = { Icon(Icons.Default.Add, contentDescription = "Add Product") }
            )
            DropdownMenuItem(
                text = { Text("Reports") },
                onClick = {
                    isFabMenuExpanded = false
                    /* TODO: Handle reports navigation */
                },
                leadingIcon = { Icon(Icons.Default.ThumbUp, contentDescription = "Reports") }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductListContent(
    productList: Flow<List<Product>>,
    onRemoveProduct: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val products by productList.collectAsState(initial = emptyList())

    FlowRow(
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Center
    ) {
        products.forEach { product ->
            Box(modifier = Modifier.width(300.dp).padding(8.dp)) {
                ProductItem(product = product, onClick = { onRemoveProduct(product) })
            }
        }
    }
}


@Composable
fun ProductItem(product: Product, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
    ) {
        Column(modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)) {
            Icon(
                Icons.Filled.Face,
                contentDescription = "Placeholder",
                modifier = Modifier.padding(16.dp).width(50.dp).height(50.dp),
            )
            Text(text = product.name, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Quantidade: ${product.quantity}")
        }
    }
}