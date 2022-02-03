package test

import api.models.Pet
import api.models.Tag
import api.requests.PetRequests
import kotlinx.serialization.json.Json
import okhttp3.Response
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import kotlinx.serialization.decodeFromString
import org.junit.jupiter.params.provider.MethodSource
import utils.StringGenerationUtils
import java.util.stream.Stream


class PetRequestsTest {
    private val petRequests: PetRequests = PetRequests()


    companion object {
        @JvmStatic
        fun validPetsProvider(): Stream<Arguments?>? {
            return Stream.of(
                Arguments.of(Pet(id = 1, name = StringGenerationUtils.getRandomStringOfLength(8))),
            )
        }

        @JvmStatic
        fun validPetsAndUpdatesProvider(): Stream<Arguments?>? {

            val petWithName = Pet(
                id = 1,
                name = StringGenerationUtils.getRandomStringOfLength(8)
            );
            val petWithUpdatedName = Pet(
                id = 1,
                name = StringGenerationUtils.getRandomStringOfLength(8)
            );

            val petWithTags = Pet(
                id = 2,
                name = StringGenerationUtils.getRandomStringOfLength(8),
                tags = listOf(Tag(2, StringGenerationUtils.getRandomStringOfLength(8)))
            );
            val petWithUpdatedTags = Pet(
                id = 2,
                name = StringGenerationUtils.getRandomStringOfLength(8),
                tags = listOf(Tag(2, StringGenerationUtils.getRandomStringOfLength(8)))
            );
            return Stream.of(
                Arguments.of(petWithName, petWithUpdatedName),
                Arguments.of(petWithTags, petWithUpdatedTags)
            )
        }

        @JvmStatic
        fun invalidIdUpdatePet(): Stream<Arguments?>? {
            val petWithName = Pet(
                id = 1,
                name = StringGenerationUtils.getRandomStringOfLength(8)
            );
            val petWithUpdatedName = Pet(
                id = -1,
                name = StringGenerationUtils.getRandomStringOfLength(8)
            );
            return Stream.of(
                Arguments.of(petWithName, petWithUpdatedName)
            )
        }
    }

    @ParameterizedTest
    @MethodSource("validPetsProvider")
    fun `Test send valid pet to the addANewPetToTheStore and check status code`(pet: Pet) {
        val response: Response = petRequests.addANewPetToTheStore(pet)

        Assertions.assertEquals(200, response.code);
    }

    @ParameterizedTest
    @MethodSource("validPetsProvider")
    fun `Test send valid pet to the addANewPetToTheStore and check id and name`(expectedPet: Pet) {
        val response: Response = petRequests.addANewPetToTheStore(expectedPet)

        val actualPet: Pet = Json.decodeFromString(response.body!!.string());
        Assertions.assertEquals(expectedPet, actualPet);
    }

    @ParameterizedTest
    @MethodSource("validPetsAndUpdatesProvider")
    fun `Test send valid pet to the updateAnExistingPet and check status code`(validPet: Pet, validUpdatedPet: Pet) {
        var response: Response = petRequests.addANewPetToTheStore(validPet)

        Assertions.assertEquals(200, response.code);

        response = petRequests.updateAnExistingPet(validUpdatedPet)

        Assertions.assertEquals(200, response.code);
    }

    @ParameterizedTest
    @MethodSource("validPetsAndUpdatesProvider")
    fun `Test send valid pet to the updateAnExistingPet and check updated data`(validPet: Pet, validUpdatedPet: Pet) {
        var response: Response = petRequests.addANewPetToTheStore(validPet)

        Assertions.assertEquals(200, response.code);

        response = petRequests.updateAnExistingPet(validUpdatedPet)

        val actualPet: Pet = Json.decodeFromString(response.body!!.string());
        Assertions.assertEquals(validUpdatedPet, actualPet);
    }

    @ParameterizedTest
    @MethodSource("invalidIdUpdatePet")
    fun `Test send invalid pet to the updateAnExistingPet and check not found code`(validPet: Pet, validUpdatedPet: Pet) {
        var response: Response = petRequests.addANewPetToTheStore(validPet)

        Assertions.assertEquals(200, response.code);

        response = petRequests.updateAnExistingPet(validUpdatedPet)

        Assertions.assertEquals(400, response.code);
    }

    @ParameterizedTest
    @MethodSource("validPetsProvider")
    fun `Test findPetById with valid pet and check status code`(expectedPet: Pet) {
        var response: Response = petRequests.addANewPetToTheStore(expectedPet)

        Assertions.assertEquals(200, response.code)

        response = petRequests.findPetById(expectedPet)

        Assertions.assertEquals(200, response.code)
    }

    @ParameterizedTest
    @MethodSource("validPetsProvider")
    fun `Test findPetById with valid pet and check if correct pet was returned`(expectedPet: Pet) {
        var response: Response = petRequests.addANewPetToTheStore(expectedPet)

        Assertions.assertEquals(200, response.code)

        response = petRequests.findPetById(expectedPet)

        val actualPet: Pet = Json.decodeFromString(response.body!!.string())
        Assertions.assertEquals(expectedPet, actualPet)
    }

    @ParameterizedTest
    @MethodSource("validPetsProvider")
    fun `Test deleteAPet and check the pet was deleted`(expectedPet: Pet) {
        var response: Response = petRequests.addANewPetToTheStore(expectedPet)

        Assertions.assertEquals(200, response.code)

        response = petRequests.deleteAPet(expectedPet)

        Assertions.assertEquals(200, response.code)
    }

    @ParameterizedTest
    @MethodSource("validPetsProvider")
    fun `Test deleteAPet send delete request twice and check the pet was not found second time`(expectedPet: Pet) {
        var response: Response = petRequests.addANewPetToTheStore(expectedPet)

        Assertions.assertEquals(200, response.code)

        response = petRequests.deleteAPet(expectedPet)

        Assertions.assertEquals(200, response.code)

        response = petRequests.deleteAPet(expectedPet)

        Assertions.assertEquals(404, response.code)
    }

}