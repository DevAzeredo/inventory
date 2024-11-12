package dev.azeredo.domain.usecase.stockmovement

import dev.azeredo.domain.model.StockMovement
import dev.azeredo.domain.repository.StockMovementRepository
import kotlinx.coroutines.flow.Flow

class GetAllStockMovements(private val repository: StockMovementRepository) {
    suspend operator fun invoke(): Flow<List<StockMovement>> {
        return repository.getAllMovements()
    }
}