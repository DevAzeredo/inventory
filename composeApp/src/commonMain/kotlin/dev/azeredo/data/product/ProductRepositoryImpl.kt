package dev.azeredo.data.product

import Product
import ProductRepository
import dev.azeredo.data.category.CategoryDao
import dev.azeredo.data.category.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class ProductRepositoryImpl(
    private val productDao: ProductDao,
    private val categoryDao: CategoryDao
) : ProductRepository {

    override suspend fun addProduct(product: Product) {
        productDao.insertProduct(product.toEntity())
    }

    override suspend fun removeProduct(product: Product) {
        productDao.deleteProduct(product.toEntity())
    }

    override suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product.toEntity())
    }

    override suspend fun getProductById(productId: Long): Product? {
        return productDao.getProductById(productId)?.toDomain()
    }

    override suspend fun getAllProducts(): Flow<List<Product>> {
        return combine(
            productDao.getAllProducts(),
            categoryDao.getAllCategories()
        ) { productEntities, categoryEntities ->
            val categoryMap = categoryEntities.associateBy { it.id }
            productEntities.map { productEntity ->
                val category = categoryMap[productEntity.categoryId]
                    ?: throw IllegalArgumentException("Categoria com ID ${productEntity.categoryId} não encontrada")
                val product = productEntity.toDomain().copy(category = category.toDomain())
                product
            }
        }
    }
}

