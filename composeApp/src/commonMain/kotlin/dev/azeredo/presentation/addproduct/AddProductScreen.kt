package dev.azeredo.presentation.addproduct

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(navController: NavController) {
    val viewModel = koinViewModel<AddProductViewModel>()
    val uiState by viewModel.uiState.collectAsState()
    var expanded by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(title = { Text("Adicionar Produto") }, navigationIcon = {
            IconButton(onClick = {navController.navigateUp()}) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
            }
        })
    }, floatingActionButton = {
        Row {
            ExtendedFloatingActionButton(content = { Text("Salvar") }, onClick = {
                viewModel.addProduct()
                navController.navigateUp()
            }, containerColor = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            ExtendedFloatingActionButton(
                content = { Text("Cancelar") }, onClick = {navController.navigateUp()}, containerColor = Color.Gray
            )
        }
    }) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Seção de imagem
            Box(
                modifier = Modifier.size(150.dp).clickable {
                   // TODO abrir selecionador de imagem
                }, contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Face,
                    contentDescription = "Placeholder",
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Campo de nome
            OutlinedTextField(
                value = uiState.product.name,
                onValueChange = { viewModel.setName(it) },
                label = { Text("Nome do Produto") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Campo de categoria com Dropdown
            ExposedDropdownMenuBox(expanded = expanded,
                onExpandedChange = { expanded = !expanded }) {
                OutlinedTextField(
                    value = uiState.product.categoryId,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoria") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    uiState.categories.forEach { category ->
                        DropdownMenuItem(text = { Text(category) }, onClick = {
                            viewModel.setCategory(category)
                            expanded = false
                        })
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Campo de preço
            OutlinedTextField(
                value = uiState.product.price.toString(),
                onValueChange = {},
                label = { Text("Preço") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Campo de quantidade
            OutlinedTextField(
                value = uiState.product.quantity.toString(),
                onValueChange = { input ->
//                     Permite apenas números
//                    if (input.all { it.isDigit() }) {
//                        quantity = input
//                    }
                },
                label = { Text("Quantidade") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
