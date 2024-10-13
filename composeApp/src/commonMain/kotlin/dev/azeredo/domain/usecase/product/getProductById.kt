package dev.azeredo.domain.usecase.product

import Product
import ProductRepository

class GetProductById(
    private val productRepository: ProductRepository
) {
    suspend operator fun invoke(productId: Long): Product? {
        return productRepository.getProductById(productId)
    }
}