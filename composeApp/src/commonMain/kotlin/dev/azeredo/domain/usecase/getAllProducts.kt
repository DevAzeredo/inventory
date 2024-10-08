import kotlinx.coroutines.flow.Flow

class getAllProducts(private val productRepository: ProductRepository) {
    suspend operator fun invoke(categoriaId: String? = null): Flow<List<Product>> {
//        return if (categoriaId != null) {
//            productRepository.getProductsByCategory(categoriaId)
//        } else {
          return productRepository.getAllProducts()
//        }
    }
}
