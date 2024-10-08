class addProduct(private val productRepository: ProductRepository) {
    suspend operator fun invoke(product: Product) {
        if (product.name.isEmpty() || product.quantity < 0 || product.price < 0) {
            throw IllegalArgumentException("Invalid product data")
        }
        productRepository.addProduct(product)
    }
}