package dev.azeredo.domain.usecase.productimage

import ProductImage
import ProductImageRepository
import dev.azeredo.data.productimage.toEntity


class RemoveProductImageUseCase(private val repository: ProductImageRepository) {
    suspend operator fun invoke(productImage: ProductImage) {
        repository.removeProductImage(productImage.toEntity())
    }
}