package dev.azeredo.presentation.stockmovement

import Product
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastFirst
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.azeredo.domain.model.MovementType
import dev.azeredo.domain.model.StockMovement
import dev.azeredo.presentation.getDateTimeFormated
import org.koin.compose.viewmodel.koinViewModel

class StockMovementScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinViewModel<StockMovementViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Stock Movement Report") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                )
            },
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Stock Movement Report",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                header()

                LazyColumn {
                    items(uiState.stockMovementList.size) { index ->
                        val movement = uiState.stockMovementList[index]
                        val product = uiState.productsList.fastFirst { it.id == movement.productId }
                        ProductCard(product, movement)
                    }
                }
            }
        }
    }
}

@Composable
fun header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) { Text(text = "Date & time", style = MaterialTheme.typography.titleMedium) }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) { Text(text = "Product", style = MaterialTheme.typography.titleMedium) }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) { Text(text = "Reason", style = MaterialTheme.typography.titleMedium) }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) { Text(text = "Quantity", style = MaterialTheme.typography.titleMedium) }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) { Text(text = "Price", style = MaterialTheme.typography.titleMedium) }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
            ) { Text(text = "Total Price", style = MaterialTheme.typography.titleMedium) }
    }
}

@Composable
fun ProductCard(product: Product, stockMovement: StockMovement) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = getDateTimeFormated(stockMovement.movementDate),
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = product.name,
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stockMovement.reason,
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "${stockMovement.quantity}",
                color = if (stockMovement.movementType == MovementType.ENTRY) Color.Green else Color.Red,
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "$ ${product.price}",
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "$ ${stockMovement.quantity * product.price}",
                color = if (stockMovement.movementType == MovementType.ENTRY) Color.Green else Color.Red,
            )
        }
    }
}
