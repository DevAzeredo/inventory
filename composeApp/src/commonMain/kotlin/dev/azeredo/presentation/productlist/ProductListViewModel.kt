package dev.azeredo.presentation.productlist

import Product
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.azeredo.domain.usecase.product.getAllProducts
import dev.azeredo.domain.usecase.productimage.GetImageById
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class ProductListViewModel(
    private val getAllProducts: getAllProducts,
    private val getImageById: GetImageById
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val productList = getAllProducts.invoke()
            _uiState.value = _uiState.value.copy(
                productList = productList,
                productListFiltered = productList,
                searchQuery = ""
            )
            productList.collect {
                loadImagesForProducts(it)
            }
        }
    }

    private fun loadImagesForProducts(products: List<Product>) {
        viewModelScope.launch {
            val images = products.associate { product ->
                product.id to async {
                    try {
                        getImageById.invoke(product.id)
                    } catch (e: Exception) {
                        ByteArray(0)
                    }
                }
            }.mapValues { it.value.await() }

            _uiState.value = _uiState.value.copy(productImages = images)
        }
    }

    fun onSearchQueryChanged(input: String) {
        viewModelScope.launch {
            if (input.isEmpty()) {
                _uiState.value =
                    _uiState.value.copy(
                        productListFiltered = _uiState.value.productList,
                        searchQuery = input
                    )
                return@launch
            }
            val filteredList = _uiState.value.productList.map { productList ->
                productList.filter { product ->
                    product.name.contains(input, ignoreCase = true)
                }
            }
            _uiState.value =
                _uiState.value.copy(productListFiltered = filteredList, searchQuery = input)
        }
    }

    data class ProductListUiState(
        val productList: Flow<List<Product>> = flowOf(emptyList()),
        val productListFiltered: Flow<List<Product>> = flowOf(emptyList()),
        val searchQuery: String = "",
        val productImages: Map<Long, ByteArray> = emptyMap()
    )
}