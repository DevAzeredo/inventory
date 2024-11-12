package dev.azeredo.data.stockmovement

import dev.azeredo.domain.model.MovementType
import dev.azeredo.domain.model.StockMovement

fun StockMovementEntity.toDomainModel(): StockMovement {
    return StockMovement(
        id = id,
        productId = productId,
        quantity = quantity,
        movementType = MovementType.valueOf(movementType),
        movementDate = movementDate,
        reason = reason
    )
}

fun StockMovement.toEntity(): StockMovementEntity {
    return StockMovementEntity(
        id = id,
        productId = productId,
        quantity = quantity,
        movementType = movementType.name,
        movementDate = movementDate,
        reason = reason
    )
}
