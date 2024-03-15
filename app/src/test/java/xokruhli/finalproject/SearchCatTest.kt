package xokruhli.finalproject

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import xokruhli.finalproject.model.UiState
import xokruhli.finalproject.model.catApi.Breed
import xokruhli.finalproject.ui.screens.searchBreed.SearchBreedErrors
import xokruhli.finalproject.ui.screens.searchBreed.SearchBreedViewModel

class SearchCatTest {

    private lateinit var viewModel: SearchBreedViewModel

    @Before
    fun setup() {
        viewModel = Mockito.mock(SearchBreedViewModel::class.java)
    }
    @Test
    fun findByNameSuccess() {
        val expectedBreeds = listOf(
            Breed(
                id = "abys",
                name = "Abyssinian",
                weight = null,
                image = null,
                origin = "Egypt",
                temperament = "Active, Energetic, Independent, Intelligent, Gentle",
                countryCodes = null,
                countryCode = null,
                description = null,
                lifeSpan = null,
                indoor = null,
                lap = null,
                altNames = null,
                adaptability = null,
                affectionLevel = null,
                childFriendly = null,
                dogFriendly = null,
                energyLevel = null,
                grooming = null,
                healthIssues = null,
                intelligence = null,
                sheddingLevel = null,
                socialNeeds = null,
                strangerFriendly = null,
                vocalisation = null,
                experimental = null,
                hairless = null,
                natural = null,
                rare = null,
                rex = null,
                suppressedTail = null,
                shortLegs = null,
                wikipediaUrl = null,
                hypoallergenic = null,
                referenceImageId = null,
                cfaUrl = null,
                vcahospitalsUrl = null,
                vetstreetUrl = null
            )
        )
        val expectedState = UiState(
            data = expectedBreeds,
            loading = false,
            errors = SearchBreedErrors(2)
        )

        val view = viewModel.findBreedByName("Abyssinian")
        Assert.assertNotEquals(view, null)
    }

    @Test
    fun SearchByNameNotSuccess() {
        val expectedBreeds = listOf(
            Breed(
                id = "abys",
                name = "Abyssinian",
                weight = null,
                image = null,
                origin = "Egypt",
                temperament = "Active, Energetic, Independent, Intelligent, Gentle",
                countryCodes = null,
                countryCode = null,
                description = null,
                lifeSpan = null,
                indoor = null,
                lap = null,
                altNames = null,
                adaptability = null,
                affectionLevel = null,
                childFriendly = null,
                dogFriendly = null,
                energyLevel = null,
                grooming = null,
                healthIssues = null,
                intelligence = null,
                sheddingLevel = null,
                socialNeeds = null,
                strangerFriendly = null,
                vocalisation = null,
                experimental = null,
                hairless = null,
                natural = null,
                rare = null,
                rex = null,
                suppressedTail = null,
                shortLegs = null,
                wikipediaUrl = null,
                hypoallergenic = null,
                referenceImageId = null,
                cfaUrl = null,
                vcahospitalsUrl = null,
                vetstreetUrl = null
            )
        )
        val expectedState = UiState(
            data = expectedBreeds,
            loading = false,
            errors = SearchBreedErrors(2)
        )

        val view = viewModel.findBreedByName("tohleFaktNe")
        Assert.assertNotEquals(view, expectedState)
    }

}