package dev.azeredo.presentation.addproduct

import Product
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.azeredo.domain.model.Category
import dev.azeredo.domain.usecase.category.AddCategory
import dev.azeredo.domain.usecase.category.GetAllCategories
import dev.azeredo.domain.usecase.product.AddProduct
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddProductViewModel(
    private val addProduct: AddProduct,
    private val addCategory: AddCategory,
    private val getAllCategories: GetAllCategories
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddProductUiState())
    val uiState: StateFlow<AddProductUiState> get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getAllCategories.invoke().collect { c ->
                _uiState.value =
                    _uiState.value.copy(categories = c)
            }
        }
    }

    fun addProduct() {
        viewModelScope.launch {
            addProduct.invoke(_uiState.value.product)
        }
    }

    fun addCategory(newCategoryName: String) {
        viewModelScope.launch {
            addCategory.invoke(Category(0, newCategoryName))
        }
    }

    fun setCategory(newCategory: Category) {
        _uiState.value =
            _uiState.value.copy(product = _uiState.value.product.copy(category = newCategory))
    }

    fun setName(newName: String) {
        _uiState.value = _uiState.value.copy(product = _uiState.value.product.copy(name = newName))
    }

    fun setPrice(newPrice: Double) {
        _uiState.value =
            _uiState.value.copy(product = _uiState.value.product.copy(price = newPrice))
    }

    fun setQuantity(newQuantity: Double) {
        _uiState.value =
            _uiState.value.copy(product = _uiState.value.product.copy(quantity = newQuantity))
    }

    data class AddProductUiState(
        val product: Product = Product(
            id = 0,
            name = "",
            price = 0.0,
            quantity = 0.0,
            category = Category(0, ""),
            creationDate = 0,
            updateDate = 0
        ),
        val categories: List<Category> = emptyList()
    )
}