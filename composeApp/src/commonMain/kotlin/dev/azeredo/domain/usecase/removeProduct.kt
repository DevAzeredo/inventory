class removeProduct(private val productRepository: ProductRepository) {
    suspend operator fun invoke(product: Product) {
        if (productRepository.getProductById(product.id) == null) {
            throw NoSuchElementException("Produto não encontrado")
        }
        productRepository.removeProduct(product)
    }
}
