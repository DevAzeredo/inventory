package dev.azeredo.data.productimage

import ProductImage
import ProductImageRepository
import dev.azeredo.data.category.ProductImageEntity

class ProductImageRepositoryImpl(private val productDao: ProductImageDao) : ProductImageRepository {
    override suspend fun addProductImage(productId: Long, image: ByteArray) {
        val productImage = ProductImage(productId = productId, image = image)
        val imageExist = productDao.getByProductId(productId)
        if (imageExist.image.isNotEmpty()) {
            productDao.deleteProductImage(imageExist)
        }
        productDao.insertProductImage(productImage.toEntity())
    }

    override suspend fun removeProductImage(productImage: ProductImageEntity) {
        productDao.deleteProductImage(productImage)
    }

    override suspend fun getImageById(productId: Long): ByteArray {
        return productDao.getByProductId(productId).image ?: ByteArray(0)
    }


}
