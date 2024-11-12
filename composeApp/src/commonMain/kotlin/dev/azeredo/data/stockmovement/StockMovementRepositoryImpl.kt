package dev.azeredo.data.stockmovement

import Product
import androidx.room.Transaction
import dev.azeredo.domain.model.StockMovement
import dev.azeredo.domain.repository.StockMovementRepository
import dev.azeredo.domain.usecase.product.UpdateProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StockMovementRepositoryImpl(
    private val dao: StockMovementDao,
    private val updateProduct: UpdateProduct

) : StockMovementRepository {

    override suspend fun addMovement(movement: StockMovement) {
        dao.insert(movement.toEntity())
    }

    override suspend fun getMovementsByProduct(productId: Long): List<StockMovement> {
        return dao.getMovementsByProduct(productId).map { it.toDomainModel() }
    }

    @Transaction
    override suspend fun saveMovements(
        movements: List<StockMovement>,
        products: List<Product>
    ) {
        movements.forEach { addMovement(it) }
        products.forEach { updateProduct(it) }
    }

    override suspend fun getAllMovements(): Flow<List<StockMovement>> {
        return dao.getAllMovements().map {
            it.map { stockMovementEntity ->
                stockMovementEntity.toDomainModel()
            }
        }
    }

}
