
interface ProductImageRepository {
    suspend fun addProductImage(productId: Long, image: ByteArray)
    suspend fun removeProductImage(productId: Long)
    suspend fun getProductImage(productId: Long): ProductImage?
}

