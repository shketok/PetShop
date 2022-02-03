package config


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class PetStoreConfiguration(@SerialName("base_api_url") val baseApiUrl: String)

object ConfigurationReader {
    private const val CONFIG_PATH = "src/test/resources/petstore_config.json"

    val configuration: PetStoreConfiguration by lazy { readConfig() }

    private fun readConfig(): PetStoreConfiguration {
        val jsonString: String = File(CONFIG_PATH).readText(Charsets.UTF_8)
        return Json.decodeFromString(jsonString);
    }

}