package xokruhli.finalproject.ui.screens.map

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import xokruhli.finalproject.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import xokruhli.finalproject.R
import xokruhli.finalproject.architecture.BaseViewModel
import xokruhli.finalproject.architecture.CommunicationResult
import xokruhli.finalproject.communication.places.IPlacesRemoteRepository
import xokruhli.finalproject.model.googlePlacesApi.Place
import javax.inject.Inject

@HiltViewModel
class MapViewModel
@Inject constructor(private val repository: IPlacesRemoteRepository)
    : BaseViewModel() {

    val uiState: MutableState<UiState<Place, MapError>> = mutableStateOf(UiState())
    val placesUiState: MutableState<UiState<List<Place>, MapError>> = mutableStateOf(UiState())
    init {
        loadData()
    }

    fun loadData() {
        launch {
            val response = withContext(Dispatchers.IO) {
                repository.searchNearbyVeterinary(
                    location = "49.195060,16.606837",
                    radius = 5000,
                    apiKey = "AIzaSyBjWSfXMjM9MKDwzWYMVz8Ad-KAH61i2qU"
                )
            }
            when (response) {
                is CommunicationResult.Error -> {
                    println(response.error.toString())
                    uiState.value = UiState(
                        loading = false,
                        data = null,
                        errors = MapError(R.string.failed_to_load_the_list)
                    )
                }

                is CommunicationResult.Exception -> {
                    uiState.value = UiState(
                        loading = false,
                        data = null,
                        errors = MapError(R.string.unknown_error)
                    )
                }

                is CommunicationResult.ConnectionError -> {
                    uiState.value = UiState(
                        loading = false,
                        data = null,
                        errors = MapError(R.string.no_internet_connection)
                    )
                }

                is CommunicationResult.Success -> {
                    uiState.value = UiState(
                        loading = false,
                        data = response.data,
                        errors = null
                    )
                }
            }
        }
    }
}