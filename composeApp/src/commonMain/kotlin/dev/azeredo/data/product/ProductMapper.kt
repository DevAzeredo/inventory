package dev.azeredo.data.product

import Product
import dev.azeredo.domain.model.Category

fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        id = this.id,
        name = this.name,
        categoryId = this.category.id,
        price = this.price,
        quantity = this.quantity,
        creationDate = this.creationDate,
        updateDate = this.updateDate,
//        image = this.image
    )
}

fun ProductEntity.toDomain(): Product {
    return Product(
        id = this.id,
        name = this.name,
        category = Category(this.categoryId, ""),
        price = this.price,
        quantity = this.quantity,
        creationDate = this.creationDate,
        updateDate = this.updateDate,
//        image = this.image
    )
}