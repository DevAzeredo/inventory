package dev.azeredo.domain.usecase.category

import dev.azeredo.domain.model.Category
import dev.azeredo.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow


class GetAllCategories(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(): Flow<List<Category>> {
        return categoryRepository.getAllCategories()
    }
}