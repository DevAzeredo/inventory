import dev.azeredo.domain.model.Category
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Long = 0,
    val name: String,
    val category: Category,
    val price: Double, // Shame to use Double, but what can we do, KMP doesn't have BigDecimal yet // TODO change to BigDecimal when implemented
    val quantity: Double,
    val creationDate: Long,
    val updateDate: Long,
//    val image: ByteArray? = null
)