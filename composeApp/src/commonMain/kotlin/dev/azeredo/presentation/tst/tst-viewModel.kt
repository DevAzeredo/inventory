package dev.azeredo.presentation.tst

import Product
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.azeredo.data.AppDatabase
import dev.azeredo.data.ProductEntity
import dev.azeredo.data.toEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: AppDatabase) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> get() = _uiState.asStateFlow()

    init {

        viewModelScope.launch {
            repository.getProductDao().insertProduct(Product(
                    name = "asdasdasd",
                    price = 1.0,
                    quantity = 1,
                    categoryId = "asd",
                    creationDate = 1,
                    updateDate = 1,
                    id = 0
                ).toEntity())
            repository.getProductDao().getAllProducts().collect { productList ->
                _uiState.value = _uiState.value.copy(
                    productList = productList
                )
            }
        }
    }

    fun addProduct(name: String, price: Double) {
//        viewModelScope.launch {
//            repository.addProduct(
//                Product(
//                    name = name,
//                    price = price,
//                    quantity = 1,
//                    categoryId = "asd",
//                    creationDate = 1,
//                    updateDate = 1,
//                    id = 0
//                )
//            )
//        }
    }

    fun removeProduct(product: Product) {
//        viewModelScope.launch {
//            repository.delete(product)
//        }
    }

    data class ProductUiState(
        val productList: List<ProductEntity> = emptyList(),
    )
}