package dev.azeredo.presentation.outbound

import Product
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.azeredo.domain.model.MovementType
import dev.azeredo.domain.model.StockMovement
import dev.azeredo.domain.usecase.product.getAllProducts
import dev.azeredo.domain.usecase.stockmovement.SaveMovements
import dev.azeredo.presentation.UiMessage
import dev.azeredo.presentation.getDecimalRegex
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock


class OutboundViewModel(
    private val saveOutboundMovements: SaveMovements,
    private val getAllProducts: getAllProducts,
) : ViewModel() {
    private val _uiState = MutableStateFlow(OutboundUiState())
    val uiState: StateFlow<OutboundUiState> get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val productList = getAllProducts.invoke()
            _uiState.value = _uiState.value.copy(
                productList = productList,
            )
        }
    }

    fun saveStockMovements() {
        viewModelScope.launch {
            try {
                val allProducts = _uiState.value.productList.first()
                val selectedProducts = _uiState.value.selectedProductList.first()

                val movements = selectedProducts.map { createStockMovement(it) }
                val updatedProducts = selectedProducts.map { selected ->
                    val existing = allProducts.first { it.id == selected.first.id }
                    existing.copy(quantity = existing.quantity + selected.first.quantity)
                }

                saveOutboundMovements(movements, updatedProducts)

                _uiState.value = _uiState.value.copy(selectedProductList = flowOf(emptyList()))
                addUiMessage(
                    UiMessage.Success(
                        id = Clock.System.now().toEpochMilliseconds(),
                        message = "Outbound successfully registered!"
                    )
                )
            } catch (e: Exception) {
                addUiMessage(
                    UiMessage.Error(
                        id = Clock.System.now().toEpochMilliseconds(),
                        message = "Failed to register Outbound: ${e.message}"
                    )
                )
            }
        }
    }

    fun onDiscard() {
        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(selectedProductList = flowOf(emptyList()), quantity = 0.0)
        }
    }

    private fun createStockMovement(productWithReason: Pair<Product, String>): StockMovement {
        return StockMovement(
            productId = productWithReason.first.id,
            quantity = productWithReason.first.quantity,
            movementType = MovementType.EXIT,
            movementDate = Clock.System.now().toEpochMilliseconds(),
            reason = productWithReason.second
        )
    }

    fun addProductOutbound(product: Product) {
        viewModelScope.launch {
            if (_uiState.value.quantity < 0) {
                val currentList = _uiState.value.selectedProductList.first()

                val existingProduct = currentList.firstOrNull {
                    it.first.id == product.id
                }
                if (existingProduct != null) {
                    updateProductOutbound(existingProduct.first, _uiState.value.quantity, _uiState.value.reason)
                    return@launch
                }
                val newProduct = product.copy(quantity = _uiState.value.quantity)
                val reason = _uiState.value.reason
                updateProductList(currentList + (newProduct to reason))
            }
        }
    }

    fun updateProductOutbound(product: Product, amount: Double, reason: String) {
        viewModelScope.launch {
            val currentList = _uiState.value.selectedProductList.first()

            val updatedProductList = currentList.mapNotNull { (existingProduct, existingReason) ->
                if (existingProduct.id == product.id) {
                    val newQuantity = existingProduct.quantity + amount
                    if (newQuantity < 0) existingProduct.copy(quantity = newQuantity) to reason else null
                } else {
                    existingProduct to existingReason
                }
            }

            updateProductList(updatedProductList)
        }
    }

    private fun updateProductList(newList: List<Pair<Product, String>>) {
        _uiState.value = _uiState.value.copy(selectedProductList = flowOf(newList))
    }

    private fun addUiMessage(message: UiMessage) {
        _uiState.value = _uiState.value.copy(uiMessages = _uiState.value.uiMessages + message)
    }

    fun removeUiMessageById(id: Long) {
        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(uiMessages = _uiState.value.uiMessages.filterNot { msg -> msg.id == id })
        }
    }

    fun setQuantity(newQuantity: String) {
        viewModelScope.launch {
            if (newQuantity.matches(getDecimalRegex())) _uiState.value =
                _uiState.value.copy(quantity = -newQuantity.toDouble())
        }
    }

    fun setReason(newReason: String) {
        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(reason = newReason)
        }
    }

}

data class OutboundUiState(
    val productList: Flow<List<Product>> = flowOf(emptyList()),
    val selectedProductList: Flow<List<Pair<Product, String>>> = flowOf(emptyList()),
    val quantity: Double = 0.0,
    val reason: String = "",
    val uiMessages: List<UiMessage> = emptyList()
)
