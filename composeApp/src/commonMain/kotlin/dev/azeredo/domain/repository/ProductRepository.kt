import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun addProduct(product: Product)
    suspend fun removeProduct(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun getProductById(productId: Int): Product?
    suspend fun getAllProducts(): Flow<List<Product>>
}