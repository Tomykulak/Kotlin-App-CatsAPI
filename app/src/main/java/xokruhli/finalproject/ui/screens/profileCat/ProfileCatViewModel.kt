package xokruhli.finalproject.ui.screens.profileCat

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import xokruhli.finalproject.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import xokruhli.finalproject.R
import xokruhli.finalproject.architecture.BaseViewModel
import xokruhli.finalproject.database.ICatsRepository
import xokruhli.finalproject.model.profile.Cat
import javax.inject.Inject

@HiltViewModel
class ProfileCatViewModel
@Inject constructor(private val repository: ICatsRepository) : BaseViewModel() {
    val uiState: MutableState<UiState<Cat, ProfileCatErrors>> = mutableStateOf(UiState())

    fun loadData(catId: Long) {
        launch {
            repository.getCatById(catId = catId)
                .catch { e ->
                    uiState.value = UiState(
                        loading = false,
                        data = null,
                        errors = ProfileCatErrors(R.string.unknown_error)
                    )
                }
                .collect { cat ->
                    uiState.value = UiState(
                        loading = false,
                        data = cat,
                        errors = null
                    )
                }
        }
    }

    fun deleteCat(cat: Cat){
        launch {
            repository.deleteCat(cat)
        }
    }
}

