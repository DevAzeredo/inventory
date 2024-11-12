package dev.azeredo.domain.repository

import Product
import dev.azeredo.domain.model.StockMovement
import kotlinx.coroutines.flow.Flow

interface StockMovementRepository {
    suspend fun addMovement(movement: StockMovement)
    suspend fun getMovementsByProduct(productId: Long): List<StockMovement>
    suspend fun saveMovements(movements: List<StockMovement>, products: List<Product>)
    suspend fun getAllMovements(): Flow<List<StockMovement>>
}