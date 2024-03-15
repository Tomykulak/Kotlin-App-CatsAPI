package xokruhli.finalproject.ui.screens.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import xokruhli.finalproject.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xokruhli.finalproject.architecture.BaseViewModel
import xokruhli.finalproject.database.ICatsRepository
import xokruhli.finalproject.model.profile.Cat
import javax.inject.Inject
@HiltViewModel
class ProfileViewModel
@Inject constructor(private val repository: ICatsRepository)
    : BaseViewModel() {

    val uiState: MutableState<UiState<List<Cat>, ProfileError>> = mutableStateOf(UiState(loading = false))

    init {
        launch {
            repository.getAll().collect {
                uiState.value = UiState(data = it, errors = null)
            }

        }
    }

    fun deleteCat(cat: Cat){
        launch {
            repository.deleteCat(cat)
        }
    }

}