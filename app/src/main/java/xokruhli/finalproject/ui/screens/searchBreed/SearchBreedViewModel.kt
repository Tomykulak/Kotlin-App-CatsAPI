package xokruhli.finalproject.ui.screens.searchBreed

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import xokruhli.finalproject.communication.pets.PetsRemoteRepositoryImpl
import xokruhli.finalproject.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xokruhli.finalproject.R
import xokruhli.finalproject.architecture.BaseViewModel
import xokruhli.finalproject.architecture.CommunicationResult
import xokruhli.finalproject.model.catApi.Breed
import javax.inject.Inject

@HiltViewModel
class SearchBreedViewModel @Inject constructor(
    private val petsRemoteRepositoryImpl: PetsRemoteRepositoryImpl
) : BaseViewModel()
{
    init {
        loadCats()
    }

    val petsUIState: MutableState<UiState<List<Breed>, SearchBreedErrors>>
            = mutableStateOf(UiState())

    private var allBreeds: List<Breed> = listOf()
    val isSearchResultEmpty = mutableStateOf(false)

    fun loadCats(){
        launch {
            val result = withContext(Dispatchers.IO) {
                petsRemoteRepositoryImpl.getBreeds()
            }
            when (result) {
                is CommunicationResult.ConnectionError -> {
                    petsUIState.value = UiState(loading = false, null,
                        errors = SearchBreedErrors(R.string.no_internet_connection1)
                    )
                }
                is CommunicationResult.Error -> {
                    petsUIState.value = UiState(loading = false, null,
                        errors = SearchBreedErrors(R.string.failed_to_load_the_list1)
                    )
                    Log.d("LoadingError", result.toString())
                }
                is CommunicationResult.Exception -> {
                    petsUIState.value = UiState(loading = false, null,
                        errors = SearchBreedErrors(R.string.unknown_error1)
                    )
                }
                is CommunicationResult.Success -> {
                    if(result.data.isNotEmpty()) {
                        allBreeds = result.data
                        petsUIState.value = UiState(
                            loading = false,
                            result.data,
                            errors = null
                        )
                    } else {
                        petsUIState.value = UiState(
                            loading = false,
                            data = null,
                            errors = SearchBreedErrors(
                                R.string.list_is_empty
                            )
                        )
                    }
                }
            }
        }
    }

    fun findBreedByName(name: String) {
        if (name.isNotBlank()) {
            val filteredBreeds = allBreeds.filter {
                it.name?.lowercase()?.contains(name.lowercase()) == true
            }
            isSearchResultEmpty.value = filteredBreeds.isEmpty()
            petsUIState.value = UiState(
                loading = false,
                data = filteredBreeds,
                errors = null
            )
        } else {
            isSearchResultEmpty.value = false
            petsUIState.value = UiState(
                loading = false,
                data = allBreeds,
                errors = null
            )
        }
    }
}