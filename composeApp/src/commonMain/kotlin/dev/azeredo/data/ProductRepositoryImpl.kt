package dev.azeredo.data

import Product
import ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProductRepositoryImpl(private val productDao: ProductDao) : ProductRepository {


    override suspend fun addProduct(product: Product) {
        productDao.insertProduct(product.toEntity())
    }

    override suspend fun removeProduct(product: Product) {
        productDao.deleteProduct(product.toEntity())
    }

    override suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product.toEntity())
    }

    override suspend fun getProductById(productId: Int): Product? {
        return productDao.getProductById(productId)?.toDomain()
    }

    override suspend fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAllProducts()
            .map { list ->
                list.map { it.toDomain() }
            }
    }
}

