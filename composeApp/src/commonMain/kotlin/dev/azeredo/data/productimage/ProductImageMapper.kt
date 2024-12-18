package dev.azeredo.data.productimage

import ProductImage
import dev.azeredo.data.category.ProductImageEntity


fun ProductImage.toEntity(): ProductImageEntity {
    return ProductImageEntity(
        productId = this.productId,
        image = this.image,
    )
}

fun ProductImage.toDomain(): ProductImage{
    return ProductImage(
        productId = this.productId,
        image = this.image,
    )
}