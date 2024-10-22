package dev.azeredo.domain.usecase.stockmovement

import dev.azeredo.domain.model.StockMovement
import dev.azeredo.domain.repository.StockMovementRepository

class AddStockMovement(private val repository: StockMovementRepository) {
    suspend operator fun invoke(movement: StockMovement) {
        if (movement.quantity == 0.0) {
            throw IllegalArgumentException("Quantity cannot be zero")
        }
        repository.addMovement(movement)
    }
}