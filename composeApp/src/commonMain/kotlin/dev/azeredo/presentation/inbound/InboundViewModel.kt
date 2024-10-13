package dev.azeredo.presentation.inbound

import Product
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.azeredo.domain.usecase.product.getAllProducts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch


class InboundViewModel(
    getAllProducts: getAllProducts
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
        val existingProduct = _uiState.value.selectedProductList.collect()

        if (existingProduct != null) {
            // Se o produto já está na lista, aumentar a quantidade
            val updatedQuantity = existingProduct.quantity + amount
            selectedProductList = selectedProductList.map {
                if (it.product.id == product.id) it.copy(quantity = updatedQuantity) else it
            }
        } else {
            // Se o produto não está na lista, adicionar como novo item
            selectedProductList = selectedProductList + ProductWithQuantity(product = product, quantity = amount)
        }
    } }

    data class InboundUiState(
        val productList: Flow<List<Product>> = flowOf(emptyList()),
        val selectedProductList: Flow<List<Product>> = flowOf(emptyList())
    )
}