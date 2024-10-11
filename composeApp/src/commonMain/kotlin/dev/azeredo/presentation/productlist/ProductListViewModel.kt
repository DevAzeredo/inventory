package dev.azeredo.presentation.productlist

import Product
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.azeredo.data.AppDatabase
import dev.azeredo.domain.usecase.product.getAllProducts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class ProductListViewModel(
    private val repository: AppDatabase,
    private val getAllProducts: getAllProducts
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val productList = getAllProducts.invoke()
            _uiState.value = _uiState.value.copy(
                productList = productList,
                productListFiltered = productList
            )
        }
    }

    fun onSearchQueryChanged(input: String) {
        viewModelScope.launch {
            if (input.isEmpty()) {
                _uiState.value =
                    _uiState.value.copy(productListFiltered = _uiState.value.productList)
                return@launch
            }
            val filteredList = _uiState.value.productList.map { productList ->
                productList.filter { product ->
                    product.name.contains(input, ignoreCase = true)
                }
            }
            _uiState.value = _uiState.value.copy(productListFiltered = filteredList)
        }
    }
    fun onRemoveProduct(input: String) {
        viewModelScope.launch {

        }
    }

    data class ProductListUiState(
        val productList: Flow<List<Product>> = flowOf(emptyList()),
        val productListFiltered: Flow<List<Product>> = flowOf(emptyList()),
    )
}