package api.models

@kotlinx.serialization.Serializable
data class Category(
    var id: Int = 0,
    var name: String = ""
)