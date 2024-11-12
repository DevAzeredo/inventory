package dev.azeredo.data.stockmovement

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "stock_movement")
@Serializable
data class StockMovementEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val productId: Long,
    val quantity: Double,
    val movementType: String,
    val movementDate: Long,
    val reason:String,
)