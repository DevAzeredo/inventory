package dev.azeredo.data

import androidx.room.Dao
import androidx.room.Query
@Dao
interface DatabaseUtilsDao {
    @Query("SELECT CURRENT_TIMESTAMP")
    suspend fun getCurrentTimestamp(): String
}