package dev.azeredo.domain.usecase.category

import dev.azeredo.domain.model.Category
import dev.azeredo.domain.repository.CategoryRepository

class AddCategory(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(category: Category) {
        categoryRepository.addCategory(category)
    }
}