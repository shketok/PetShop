package api.requests

import api.models.Pet
import config.ConfigurationReader
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody


class PetRequests {
    private val client = OkHttpClient()
    private val mediaType = "application/json; charset=utf-8".toMediaType()

    fun addANewPetToTheStore(pet: Pet): Response {
        val body = Json.encodeToString(pet).toRequestBody(mediaType)

        val request: Request = Request.Builder()
            .url(ConfigurationReader.configuration.baseApiUrl + "/pet")
            .post(body)
            .header("Content-Type", "application/json")
            .build()

        val call: Call = client.newCall(request)
        return call.execute();
    }

    fun updateAnExistingPet(pet: Pet): Response {
        val body = Json.encodeToString(pet).toRequestBody(mediaType)

        val request: Request = Request.Builder()
            .url(ConfigurationReader.configuration.baseApiUrl + "/pet")
            .put(body)
            .header("Content-Type", "application/json")
            .build()

        val call: Call = client.newCall(request)
        return call.execute();

    }

    fun findPetById(pet: Pet): Response {
        val request: Request = Request.Builder()
            .url(ConfigurationReader.configuration.baseApiUrl + "/pet/" + pet.id)
            .get()
            .build()

        val call: Call = client.newCall(request)
        return call.execute();

    }

    fun deleteAPet(pet: Pet): Response {
        val body = Json.encodeToString(pet).toRequestBody(mediaType)

        val request: Request = Request.Builder()
            .url(ConfigurationReader.configuration.baseApiUrl + "/pet" + pet.id)
            .delete(body)
            .build()

        val call: Call = client.newCall(request)
        return call.execute();

    }
}