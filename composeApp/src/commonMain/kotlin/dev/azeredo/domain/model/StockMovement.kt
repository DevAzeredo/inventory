package dev.azeredo.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class StockMovement(
    val id: Long = 0,
    val productId: Long,
    val quantity: Double,
    val movementType: MovementType,
    val movementDate: Long,
    val reason:String
)

enum class MovementType {
    ENTRY, EXIT
}