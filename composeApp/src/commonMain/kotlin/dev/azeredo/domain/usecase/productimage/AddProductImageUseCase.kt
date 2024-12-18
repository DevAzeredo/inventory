package dev.azeredo.domain.usecase.productimage

import ProductImageRepository

class AddProductImageUseCase(private val repository: ProductImageRepository) {
    suspend operator fun invoke(productId: Long, image: ByteArray) {
        repository.addProductImage(productId, image)
    }
}

