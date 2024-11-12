package dev.azeredo.data.stockmovement

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StockMovementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movement: StockMovementEntity)

    @Query("SELECT * FROM stock_movement WHERE productId = :productId")
    suspend fun getMovementsByProduct(productId: Long): List<StockMovementEntity>
    @Query("SELECT * FROM stock_movement")
     fun getAllMovements(): Flow<List<StockMovementEntity>>

}
