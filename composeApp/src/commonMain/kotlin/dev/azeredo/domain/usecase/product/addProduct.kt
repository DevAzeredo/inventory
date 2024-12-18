package dev.azeredo.domain.usecase.product

import Product
import ProductRepository

class AddProduct(private val productRepository: ProductRepository) {
    suspend operator fun invoke(product: Product) {
        if (product.name.isEmpty() || product.quantity < 0 || product.price < 0) {
            throw IllegalArgumentException("Invalid product data")
        }
        if (product.id > 0) {
            productRepository.updateProduct(product)
        } else {
            productRepository.addProduct(product)
        }
    }
}