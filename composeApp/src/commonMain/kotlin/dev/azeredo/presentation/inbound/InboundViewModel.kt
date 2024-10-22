package dev.azeredo.presentation.inbound

import Product
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.azeredo.domain.model.MovementType
import dev.azeredo.domain.model.StockMovement
import dev.azeredo.domain.usecase.product.getAllProducts
import dev.azeredo.domain.usecase.stockmovement.AddStockMovement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch


class InboundViewModel(
    private val addStockMovement: AddStockMovement,
    private val getAllProducts: getAllProducts
) : ViewModel() {
    private val _uiState = MutableStateFlow(InboundUiState())
    val uiState: StateFlow<InboundUiState> get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val productList = getAllProducts.invoke()
            _uiState.value = _uiState.value.copy(
                productList = productList,
            )
        }
    }

    fun saveStockMovements(
        products: List<Product>,
        quantities: Map<Long, Double>,
    ) {
        viewModelScope.launch {
            products.forEach { product ->
                val quantity = quantities[product.id] ?: 0.0

                val movement = StockMovement(
                    productId = product.id,
                    quantity = quantity,
                    movementType = MovementType.ENTRY,
                    movementDate = 0
                )

                addStockMovement(movement)

                // Atualiza a quantidade total do produto
                val updatedQuantity = product.quantity + movement.quantity
                val updatedProduct = product.copy(
                    quantity = updatedQuantity,
                    updateDate = System.currentTimeMillis()
                )

                productRepository.addProduct(updatedProduct)
            }
        }
    }

    fun setProductInbound(product: Product, amount: Double) {
        viewModelScope.launch {
            val currentList = _uiState.value.selectedProductList.first()

            val existingProduct = currentList.firstOrNull { it.id == product.id }

            if (existingProduct != null) {
                val updatedQuantity = existingProduct.quantity + amount
                val updatedProductList = currentList.map {
                    if (it.id == product.id) it.copy(quantity = updatedQuantity) else it
                }
                _uiState.value =
                    _uiState.value.copy(selectedProductList = flowOf(updatedProductList))
            } else {
                val updatedProductList = currentList + product.copy(quantity = amount)
                _uiState.value =
                    _uiState.value.copy(selectedProductList = flowOf(updatedProductList))
            }
        }
    }
}

data class InboundUiState(
    val productList: Flow<List<Product>> = flowOf(emptyList()),
    val selectedProductList: Flow<List<Product>> = flowOf(emptyList())
)
