package dev.azeredo.presentation.tst

import Product
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.azeredo.data.toDomain
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun ProductScreen() {
    val viewModel = koinViewModel<ProductViewModel>()
    val uiState by viewModel.uiState.collectAsState()

    var name by remember { mutableStateOf("") }
    var categoryId by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Gerenciar Produtos", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        // Formulário de Adição
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome do Produto") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = categoryId,
            onValueChange = { categoryId = it },
            label = { Text("ID da Categoria") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Preço") },
            modifier = Modifier.fillMaxWidth(),
//            keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions.Default.copy(
//                keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
//            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantidade") },
            modifier = Modifier.fillMaxWidth(),
//            keyboardOptions = androidx.compose.ui.text.input.KeyboardOptions.Default.copy(
//                keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
//            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (errorMessage != null) {
            Text(text = errorMessage!!/*, color = MaterialTheme.colors.error*/)
            Spacer(modifier = Modifier.height(8.dp))
        }
        Button(
            onClick = {
                val priceValue = price.toDoubleOrNull()
                val quantityValue = quantity.toIntOrNull()

                when {
                    name.isBlank() -> errorMessage = "O nome do produto não pode estar vazio."
                    categoryId.isBlank() -> errorMessage = "O ID da categoria não pode estar vazio."
                    priceValue == null || priceValue < 0 -> errorMessage =
                        "Por favor, insira um preço válido."

                    quantityValue == null || quantityValue < 0 -> errorMessage =
                        "Por favor, insira uma quantidade válida."

                    else -> {
                        viewModel.addProduct(name, 2.0);
//                        viewModel.addProduct(name, categoryId, priceValue, quantityValue)
                        name = ""
                        categoryId = ""
                        price = ""
                        quantity = ""
                        errorMessage = null
                    }
                }
            },
//            modifier = Modifier.align(LineHeightStyle.Alignment.End)
        ) {
            Text("Adicionar Produto")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Lista de Produtos
        LazyColumn {
            items(uiState.productList) { product ->
                ProductItem(product = product.toDomain(), onDelete = { viewModel.removeProduct(product.toDomain()) })
                Divider()
            }
        }
    }
}

@Composable
fun ProductItem(product: Product, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = product.name, style = MaterialTheme.typography.headlineMedium)
            Text(
                text = "Categoria: ${product.categoryId}",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Preço: R$  product.price ",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Quantidade: ${product.quantity}",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Criado em: ${product.creationDate}",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Atualizado em: ${product.updateDate}",
                style = MaterialTheme.typography.headlineMedium
            )
        }
        IconButton(onClick = onDelete) {
            Icon(
                imageVector = androidx.compose.material.icons.Icons.Default.Delete,
                contentDescription = "Deletar Produto"
            )
        }
    }
}