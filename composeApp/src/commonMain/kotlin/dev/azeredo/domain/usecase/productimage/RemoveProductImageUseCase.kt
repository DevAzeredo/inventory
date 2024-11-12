package dev.azeredo.domain.usecase.productimage

import ProductImageRepository


class RemoveProductImageUseCase(private val repository: ProductImageRepository) {
    suspend operator fun invoke(productId: Long) {
        repository.removeProductImage(productId)
    }
}