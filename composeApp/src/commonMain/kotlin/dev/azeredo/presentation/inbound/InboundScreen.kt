package dev.azeredo.presentation.inbound

import Product
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.azeredo.presentation.AppIcons
import dev.azeredo.presentation.getDecimalRegex
import kotlinx.coroutines.flow.Flow
import org.koin.compose.viewmodel.koinViewModel

class InboundScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        InboundContent(navigator)
    }
}

@Composable
fun InboundContent(navigator: Navigator) {
    val viewModel = koinViewModel<InboundViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    var quantity by remember { mutableStateOf("") }
    val selectedProductList by uiState.selectedProductList.collectAsState(initial = emptyList())

    Scaffold(
        topBar = { InboundTopBar(navigator) },
        floatingActionButton = { ProductFabMenu(selectedProductList, viewModel::saveInboundEntries) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues)
        ) {
            ProductDropdown(
                productsListFlow = uiState.productList,
                selectedProduct = selectedProduct,
                onProductSelected = { selectedProduct = it }
            )

            Spacer(Modifier.height(16.dp))

            QuantityInputField(quantity) { newQuantity ->
                if (newQuantity.matches(getDecimalRegex())) quantity = newQuantity
            }

            Spacer(Modifier.height(16.dp))

            AddButton(
                onAdd = {
                    selectedProduct?.let { product ->
                        val inboundAmount = quantity.toDoubleOrNull() ?: 0.0
                        viewModel.setProductInbound(product, inboundAmount)
                    }
                }
            )

            ProductList(
                productList = selectedProductList,
                onQuantityChange = { quantity, product ->
                    viewModel.setProductInbound(product, quantity)
                }
            )
        }
    }
}

@Composable
fun ProductFabMenu(
    onSave: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        FloatingActionButton(onClick = { expanded = !expanded }) {
            Icon(Icons.Default.Menu, contentDescription = "Open Menu")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Confirm Entry") },
                onClick = {
                    onSave()
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Discard Changes") },
                onClick = {
                    expanded = false
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InboundTopBar(navigator: Navigator) {
    TopAppBar(
        title = { Text("Product Inflow") },
        navigationIcon = {
            IconButton(onClick = { navigator.pop() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        },
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    )
}

@Composable
fun QuantityInputField(value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Quantity to add") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun AddButton(onAdd: () -> Unit) {
    FloatingActionButton(onClick = onAdd) {
        Text("Add")
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProductList(
    productList: List<Product>,
    onQuantityChange: (Double, Product) -> Unit
) {
    FlowRow(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        productList.forEach { product ->
            Box(modifier = Modifier.width(300.dp).padding(8.dp)) {
                SelectedProductCard(product, onQuantityChange)
            }
        }
    }
}

@Composable
fun SelectedProductCard(product: Product, onQuantityChange: (Double, Product) -> Unit) {
    Card(modifier = Modifier.padding(8.dp)) {
        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(text = product.name)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { if (product.quantity > 0) onQuantityChange(-1.0, product) }) {
                    Icon(AppIcons.Remove, contentDescription = "Decrease Quantity")
                }
                ProductQuantityField(value = product.quantity.toString()) { quantity ->
                    onQuantityChange(-product.quantity, product)
                    onQuantityChange(quantity.toDouble(), product)
                }
                IconButton(onClick = { onQuantityChange(+1.0, product) }) {
                    Icon(Icons.Default.Add, contentDescription = "Increase Quantity")
                }
            }
        }
    }
}

@Composable
fun ProductQuantityField(value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = {
            if (it.matches(getDecimalRegex())) onValueChange(it)
        },
        label = { Text("Quantity") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier.width(100.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDropdown(
    productsListFlow: Flow<List<Product>>,
    selectedProduct: Product?,
    onProductSelected: (Product) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val productsList by productsListFlow.collectAsState(initial = emptyList())
    val selectedText = selectedProduct?.name ?: "Select a product"

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            label = { Text("Product") },
            trailingIcon = {
                Icon(
                    Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    modifier = if (expanded) Modifier.rotate(180f) else Modifier
                )
            },
            modifier = Modifier.fillMaxWidth().menuAnchor()
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            productsList.forEach { product ->
                DropdownMenuItem(
                    text = { Text(product.name) },
                    onClick = {
                        onProductSelected(product)
                        expanded = false
                    }
                )
            }
        }
    }
}
