package dev.azeredo.presentation.inbound

import Product
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.azeredo.domain.usecase.product.getAllProducts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch


class InboundViewModel(
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

    fun setProduct(product: Product) {
//        _uiState.value = _uiState.value.copy(product = product)
    }

    fun addProduct() {
//        viewModelScope.launch {
//            addProduct.invoke(_uiState.value.product)
//        }
    }

    fun setQuantity(newQuantity: Double, product: Product) {
//        _uiState.value =
//            _uiState.value.copy(product = _uiState.value.product.copy(quantity = newQuantity))
    }

    fun addProductInbound(product: Product, amount: Double) {
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
