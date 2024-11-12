import dev.azeredo.domain.model.Category
import kotlinx.serialization.Serializable

@Serializable
data class ProductImage(
    val productId: Long,
    val image: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as ProductImage

        if (productId != other.productId) return false
        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = productId.hashCode()
        result = 31 * result + image.contentHashCode()
        return result
    }
}