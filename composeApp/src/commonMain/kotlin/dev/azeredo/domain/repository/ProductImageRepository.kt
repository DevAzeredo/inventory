import dev.azeredo.data.category.ProductImageEntity

interface ProductImageRepository {
    suspend fun addProductImage(productId: Long, image: ByteArray)
    suspend fun removeProductImage(productImage: ProductImageEntity)
    suspend fun getImageById(productId: Long):ByteArray
}

