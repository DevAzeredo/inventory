package dev.azeredo.presentation.addproduct

import Product
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.azeredo.data.AppDatabase
import dev.azeredo.data.toEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddProductViewModel(private val repository: AppDatabase) : ViewModel() {

    private val _uiState = MutableStateFlow(AddProductUiState())
    val uiState: StateFlow<AddProductUiState> get() = _uiState.asStateFlow()

    fun addProduct() {
        viewModelScope.launch {
            repository.getProductDao().insertProduct(
                _uiState.value.product.toEntity()
            )
        }
    }

    fun setCategory(newCategory: String) {
        _uiState.value =
            _uiState.value.copy(product = _uiState.value.product.copy(categoryId = newCategory))
    }

    fun setName(newName: String) {
        _uiState.value = _uiState.value.copy(product = _uiState.value.product.copy(name = newName))
    }

    fun setPrice(newPrice: Double) {
        _uiState.value = _uiState.value.copy(product = _uiState.value.product.copy(price = newPrice))
    }

    data class AddProductUiState(
        val product: Product = Product(
            id = 0,
            name = "",
            price = 0.0,
            quantity = 0,
            categoryId = "",
            creationDate = 0,
            updateDate = 0
        ),
        val categories: List<String> = listOf(
            "Categoria 1",
            "Categoria 2",
            "Categoria 3",
            "Categoria 4"
        )
    )
}