package dev.azeredo.data.category

import ProductImage
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductImageDao {
    @Insert
    suspend fun addProductImage(productImage: ProductImage)

    @Query("DELETE FROM product_images WHERE productId = :productId")
    suspend fun removeProductImage(productId: Long)

    @Query("SELECT * FROM product_images WHERE productId = :productId")
    suspend fun getProductImage(productId: Long): ProductImage?
}