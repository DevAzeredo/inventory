package dev.azeredo.data.stockmovement

import dev.azeredo.domain.model.StockMovement
import dev.azeredo.domain.repository.StockMovementRepository

class StockMovementRepositoryImpl(
    private val dao: StockMovementDao
) : StockMovementRepository {

    override suspend fun addMovement(movement: StockMovement) {
        dao.insert(movement.toEntity())
    }

    override suspend fun getMovementsByProduct(productId: Long): List<StockMovement> {
        return dao.getMovementsByProduct(productId).map { it.toDomainModel() }
    }
}
