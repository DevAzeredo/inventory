package dev.azeredo.presentation.inbound

import Product
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
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
import androidx.navigation.NavController
import dev.azeredo.presentation.productlist.ProductItem
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun InboundScreen(navController: NavController) {
    val viewModel = koinViewModel<InboundViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    var quantity by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Inflow") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Select Product:")
            ProductDropdown(
                productsListFlow = uiState.productList,
                selectedProduct = selectedProduct
            ) {
                selectedProduct = it
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Quantity to add") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            FloatingActionButton(
                onClick = {
                    selectedProduct?.let { product ->
                        val inflowAmount = quantity.toDoubleOrNull() ?: 0.0
                        viewModel.addProductInbound(product, inboundAmount)
                        navController.navigateUp()
                    }
                },
                content = { Text("Add to Stock") }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ProductDropdown(
    productsListFlow: Flow<List<Product>>,
    selectedProduct: Product?,
    onProductSelected: (Product) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val productsList by productsListFlow.collectAsState(initial = emptyList())
    val selectedText = selectedProduct?.name ?: "Select a product"

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            label = { Text("Product") },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null,
                    modifier = if (expanded) Modifier.rotate(180f) else Modifier
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
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
//        produtos adicionados
        FlowRow(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {
            products.forEach { product ->
                Box(modifier = Modifier.width(300.dp).padding(8.dp)) {
                    ProductItem(
                        product = product,
                        onClick = {
                            navController.navigate(
                                "AddProductScreen/${
                                    Json.encodeToString(product)
                                }"
                            )
                        })
                }
            }
        }
    }
}
