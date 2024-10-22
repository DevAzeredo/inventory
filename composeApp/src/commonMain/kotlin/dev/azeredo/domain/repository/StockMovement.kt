package dev.azeredo.domain.repository

import dev.azeredo.domain.model.StockMovement

interface StockMovementRepository {
    suspend fun addMovement(movement: StockMovement)
    suspend fun getMovementsByProduct(productId: Long): List<StockMovement>
}