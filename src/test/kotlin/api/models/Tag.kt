package api.models

@kotlinx.serialization.Serializable
data class Tag(
    val id: Int,
    val name: String
)