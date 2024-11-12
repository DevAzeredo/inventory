package dev.azeredo.presentation.stockmovement

import Product
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.azeredo.domain.model.StockMovement
import dev.azeredo.domain.usecase.product.getAllProducts
import dev.azeredo.domain.usecase.stockmovement.GetAllStockMovements
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class StockMovementViewModel(
    private val getAllStockMovements: GetAllStockMovements,
    private val getAllProducts: getAllProducts
) : ViewModel() {
    private val _uiState = MutableStateFlow(StockMovementUiState())
    val uiState: StateFlow<StockMovementUiState> get() = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val stockMovementList = getAllStockMovements.invoke().first()
            val productsList = getAllProducts.invoke().first()
            _uiState.value = _uiState.value.copy(
                stockMovementList = stockMovementList,
                productsList = productsList
            )
        }
    }

    data class StockMovementUiState(
        val stockMovementList: List<StockMovement> = emptyList(),
        val productsList: List<Product> = emptyList()
    )
}