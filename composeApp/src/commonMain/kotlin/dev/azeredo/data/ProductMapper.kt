package dev.azeredo.data

import Product

// Mapeia de Domain Model para Entity
fun Product.toEntity(): ProductEntity {
    return ProductEntity(
        id = this.id,
        name = this.name,
        categoryId = this.categoryId,
        price = this.price,
        quantity = this.quantity,
        creationDate = this.creationDate,
        updateDate = this.updateDate
    )
}

// Mapeia de Entity para Domain Model
fun ProductEntity.toDomain(): Product {
    return Product(
        id = this.id,
        name = this.name,
        categoryId = this.categoryId,
        price = this.price,
        quantity = this.quantity,
        creationDate = this.creationDate,
        updateDate = this.updateDate
    )
}