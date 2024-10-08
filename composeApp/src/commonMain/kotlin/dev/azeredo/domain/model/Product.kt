data class Product(
    val id: Int,
    val name: String,
    val categoryId: String,
    val price: Double, // Shame to use Double, but what can we do, KMP doesn't have BigDecimal yet // TODO change to BigDecimal when implemented
    val quantity: Int,
    val creationDate: Long,
    val updateDate: Long
)