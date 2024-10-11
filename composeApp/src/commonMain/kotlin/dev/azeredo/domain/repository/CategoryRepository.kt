package dev.azeredo.domain.repository

import dev.azeredo.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun addCategory(category: Category): Long
    suspend fun getAllCategories(): Flow<List<Category>>
    suspend fun getCategoryById(id: Long): Category?
}