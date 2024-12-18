package dev.azeredo.data.category

import dev.azeredo.domain.model.Category


fun Category.toEntity(): CategoryEntity {
    return CategoryEntity(
        id = this.id,
        description = this.description,
    )
}

fun CategoryEntity.toDomain(): Category {
    return Category(
        id = this.id,
        description = this.description,
    )
}