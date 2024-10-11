package dev.azeredo.data.category

import dev.azeredo.domain.model.Category
import dev.azeredo.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl(private val categoryDao: CategoryDao) : CategoryRepository {
    override suspend fun addCategory(category: Category) =
        categoryDao.insertCategory(category.toEntity())

    override suspend fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getCategoryById(id: Long): Category? =
        categoryDao.getCategoryById(id)?.toDomain()
}