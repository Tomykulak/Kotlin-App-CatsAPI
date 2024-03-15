package xokruhli.finalproject.ui.screens.catGame

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
class CatGameViewModel @Inject constructor(
    private val petsRemoteRepositoryImpl: PetsRemoteRepositoryImpl
) : BaseViewModel()
{
    init {
        loadCats()
    }

    val petsUIState: MutableState<UiState<List<Breed>, ListOfCatGameErrors>>
            = mutableStateOf(UiState())

    fun loadCats(){
        launch {
            val result = withContext(Dispatchers.IO) {
                petsRemoteRepositoryImpl.getBreeds()
            }
            when (result) {
                is CommunicationResult.ConnectionError -> {
                    petsUIState.value = UiState(loading = false, null,
                        errors = ListOfCatGameErrors(R.string.no_internet_connection1)
                    )
                }
                is CommunicationResult.Error -> {
                    petsUIState.value = UiState(loading = false, null,
                        errors = ListOfCatGameErrors(R.string.failed_to_load_the_list1)
                    )
                }
                is CommunicationResult.Exception -> {
                    petsUIState.value = UiState(loading = false, null,
                        errors = ListOfCatGameErrors(R.string.unknown_error1)
                    )
                }
                is CommunicationResult.Success -> {
                    if(result.data.isNotEmpty()) {
                        petsUIState.value = UiState(
                            loading = false,
                            result.data,
                            errors = null
                        )
                    } else {
                        petsUIState.value = UiState(
                            loading = false,
                            data = null,
                            errors = ListOfCatGameErrors(
                                R.string.list_is_empty
                            )
                        )
                    }
                }
            }
        }
    }
}