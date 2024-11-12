package dev.azeredo.data.category

import ProductImage
import ProductImageRepository

class ProductImageRepositoryImpl(private val productDao: ProductImageDao) : ProductImageRepository {
    override suspend fun addProductImage(productId: Long, image: ByteArray) {
        val productImage = ProductImage(productId = productId, image = image)
        productDao.addProductImage(productImage)
    }

    override suspend fun removeProductImage(productId: Long) {
        productDao.removeProductImage(productId)
    }

    override suspend fun getProductImage(productId: Long): ProductImage? {
        return productDao.getProductImage(productId)
    }
}
