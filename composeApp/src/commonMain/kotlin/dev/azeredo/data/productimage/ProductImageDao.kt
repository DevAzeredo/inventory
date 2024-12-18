package dev.azeredo.data.productimage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.azeredo.data.category.ProductImageEntity

@Dao
interface ProductImageDao {
    @Delete
    suspend fun deleteProductImage(productImageId: ProductImageEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductImage(productImage: ProductImageEntity): Long
    @Query("SELECT * FROM product_images WHERE productId = :productId")
    suspend fun getByProductId(productId: Long): ProductImageEntity
}