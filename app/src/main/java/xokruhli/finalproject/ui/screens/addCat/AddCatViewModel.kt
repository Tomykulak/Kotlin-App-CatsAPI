package xokruhli.finalproject.ui.screens.addCat

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import xokruhli.finalproject.model.UiState
import xokruhli.finalproject.architecture.BaseViewModel
import xokruhli.finalproject.database.ICatsRepository
import xokruhli.finalproject.model.profile.Cat


@HiltViewModel
class AddCatViewModel @Inject constructor(private val repository: ICatsRepository) : BaseViewModel() {
    val uiState: MutableState<UiState<Cat, AddCatErrors>> = mutableStateOf(UiState(loading = false))
    var recognizedText: MutableState<String> = mutableStateOf("")

    fun insertCat(cat: Cat) {
        viewModelScope.launch {
            if (isCatValid(cat)) {
                val result = withContext(Dispatchers.IO) {
                    repository.insert(cat)
                }
            }
        }
    }
    private fun isCatValid(cat: Cat): Boolean {
        return cat.name?.isNotBlank() == true && cat.breed?.isNotBlank() == true
    }
    fun updateCat(cat: Cat) {
        launch {
            withContext(Dispatchers.IO) {
                repository.updateCatById(cat)
            }
        }
    }

    fun getFodByID(catId: Long) {
        viewModelScope.launch {
            repository.getCatById(catId).collect { cat ->
                uiState.value = UiState(
                    loading = false,
                    data = cat,
                    errors = null
                )
            }
        }
    }

    fun processImageForText(bitmap: Bitmap) {
        val image = InputImage.fromBitmap(bitmap, 0)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                recognizedText.value = visionText.text
            }
            .addOnFailureListener { e ->
                Log.e("MLKit", "Failed to process image", e)
            }
    }
}