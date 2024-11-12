@file:OptIn(ExperimentalMaterial3Api::class)

package dev.azeredo.presentation.addproduct

import Product
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.azeredo.domain.model.Category
import dev.azeredo.presentation.getDecimalRegex
import org.koin.compose.viewmodel.koinViewModel


data class AddProductScreen(val product: Product? = null) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        AddProductScreen(
            navigator,
            product ?: Product(0, "", Category(0, ""), 0.0, 0.0, 0, 0)
        )
    }
}

@Composable
fun AddProductScreen(navigator: Navigator, product: Product) {
    val viewModel = koinViewModel<AddProductViewModel>()
    viewModel.setProduct(product)
    val uiState by viewModel.uiState.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    Scaffold(topBar = { ProductTopBar(navigator) },
        floatingActionButton = { ActionButtons(navigator, viewModel) }) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProductImage()
            Spacer(modifier = Modifier.height(16.dp))
            ProductForm(uiState, viewModel, expanded) { expanded = !expanded }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductTopBar(navigator: Navigator) {
    TopAppBar(title = { Text("Adicionar Produto") }, navigationIcon = {
        IconButton(onClick = {
            navigator.pop()
        }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
        }
    })
}

@Composable
fun ActionButtons(navigator: Navigator, viewModel: AddProductViewModel) {
    Row {
        ExtendedFloatingActionButton(content = { Text("Salvar") }, onClick = {
            viewModel.addProduct()
            navigator.pop()
        }, containerColor = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(16.dp))
        ExtendedFloatingActionButton(
            content = { Text("Cancelar") },
            onClick = { navigator.pop() },
            containerColor = Color.Gray
        )
    }
}

@Composable
fun ProductImage() {
    Box(
        modifier = Modifier.size(150.dp).clickable {
            // TODO abrir selecionador de imagem
        }, contentAlignment = Alignment.Center
    ) {
        Icon(
            Icons.Filled.Face,
            contentDescription = "Placeholder",
            modifier = Modifier.fillMaxSize().padding(16.dp)
        )
    }
}

@Composable
fun ProductForm(
    uiState: AddProductViewModel.AddProductUiState,
    viewModel: AddProductViewModel,
    expanded: Boolean,
    onExpandedChange: () -> Unit
) {
    OutlinedTextField(
        value = uiState.product.name,
        onValueChange = { viewModel.setName(it) },
        label = { Text("Nome do Produto") },
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))

    CategoryDropdown(selectedCategory = uiState.product.category,
        categories = uiState.categories,
        onCategorySelected = { viewModel.setCategory(it) },
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        onNewCategory = { newCategoryName -> viewModel.addCategory(newCategoryName) })
    Spacer(modifier = Modifier.height(16.dp))

    ProductPriceField(uiState.product.price.toString()) { newPrice ->
        viewModel.setPrice(newPrice.toDouble())
    }
    Spacer(modifier = Modifier.height(16.dp))

    ProductQuantityField(uiState.product.quantity.toString()) { newQuantity ->
        viewModel.setQuantity(newQuantity.toDouble())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    selectedCategory: Category?,
    categories: List<Category>,
    onCategorySelected: (Category) -> Unit,
    expanded: Boolean,
    onExpandedChange: () -> Unit,
    onNewCategory: (String) -> Unit
) {
    var newCategoryName by remember { mutableStateOf("") }
    var showNewCategoryField by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { onExpandedChange() }) {
        OutlinedTextField(
            value = selectedCategory?.description ?: "",
            onValueChange = {},
            label = { Text("Categoria") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            readOnly = true,
            visualTransformation = VisualTransformation.None
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { onExpandedChange() }) {
            categories.forEach { category ->
                DropdownMenuItem(text = { Text(category.description) }, onClick = {
                    onCategorySelected(category)
                    onExpandedChange()
                })
            }

            DropdownMenuItem(text = { Text("Criar nova categoria") }, onClick = {
                showNewCategoryField = true
                onExpandedChange()
            })
        }
    }

    if (showNewCategoryField) {
        Spacer(modifier = Modifier.height(8.dp))
        cardNewItem(value = newCategoryName, onValueChange = { newCategoryName = it }, onClick = {
            onNewCategory(newCategoryName)
            showNewCategoryField = false
        }, label = "Nova Categoria"
        )
    }
}

@Composable
fun cardNewItem(
    value: String, onValueChange: (String) -> Unit, onClick: () -> Unit, label: String
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        Box(Modifier.fillMaxWidth().padding(16.dp)) {
            TextField(value = value,
                onValueChange = { onValueChange(it) },
                label = { Text(label) },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(onClick = {
                        onClick()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add, contentDescription = label
                        )
                    }
                })
        }
    }
}

@Composable
fun ProductPriceField(value: String, onValueChange: (String) -> Unit) {
    val priceValue = if (value == "0.0") "" else value
    OutlinedTextField(
        value = priceValue,
        onValueChange = { input ->
            if (input.matches(getDecimalRegex())) {
                onValueChange(input)
            }
        },
        label = { Text("Price") },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun ProductQuantityField(value: String, onValueChange: (String) -> Unit) {
    val quantityValue = if (value == "0.0") "" else value
    OutlinedTextField(
        value = quantityValue,
        onValueChange = {
            if (it.matches(getDecimalRegex())) {
                onValueChange(it)
            }
        },
        label = { Text("Quantidade") },
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        modifier = Modifier.fillMaxWidth()
    )
}

