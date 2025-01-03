package dev.azeredo.domain.usecase.productimage

import ProductImageRepository

class GetImageById(private val repository: ProductImageRepository) {
    suspend operator fun invoke(productId: Long):ByteArray {
       return repository.getImageById(productId)
    }
}

