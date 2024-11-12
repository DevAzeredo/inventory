package dev.azeredo.domain.usecase.stockmovement

import Product
import dev.azeredo.domain.model.StockMovement
import dev.azeredo.domain.repository.StockMovementRepository

class SaveMovements(private val repository: StockMovementRepository) {
    suspend operator fun invoke(movements: List<StockMovement>, products: List<Product>) {
        repository.saveMovements(movements, products)
    }
}