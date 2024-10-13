package dev.azeredo.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: Long,
    val description: String
)