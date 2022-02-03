package api.models

@kotlinx.serialization.Serializable
data class Pet(
    val category: Category? = Category(),
    val id: Int,
    val name: String,
    var photoUrls: List<String>? = emptyList(),
    var status: String = "available",
    var tags: List<Tag>? = emptyList()
)