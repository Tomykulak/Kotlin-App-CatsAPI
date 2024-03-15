package xokruhli.finalproject.ui.screens.catDetail

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import xokruhli.finalproject.communication.pets.PetsRemoteRepositoryImpl
import xokruhli.finalproject.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import xokruhli.finalproject.R
import xokruhli.finalproject.architecture.BaseViewModel
import xokruhli.finalproject.architecture.CommunicationResult
import xokruhli.finalproject.datastore.IDataStoreRepository
import xokruhli.finalproject.model.catApi.Breed
import java.io.IOException
import java.io.InputStream
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PetDetailScreenViewModel @Inject constructor(
    private val petsRemoteRepositoryImpl: PetsRemoteRepositoryImpl,
    val dataStoreRepository: IDataStoreRepository
)
    : BaseViewModel() {

    val petDetailUIState: MutableState<UiState<Breed, ListOfCatDetailErrors>> =
        mutableStateOf(UiState(loading = false))



    fun loadPet(id: String) {
        launch {
            val result = withContext(Dispatchers.IO) {
                petsRemoteRepositoryImpl.getBreedById(id = id)
            }

            when (result) {
                is CommunicationResult.ConnectionError -> {
                    petDetailUIState.value = UiState(
                        loading = false, null,
                        errors = ListOfCatDetailErrors(R.string.no_internet_connection1)
                    )
                }

                is CommunicationResult.Error -> {
                    petDetailUIState.value = UiState(
                        loading = false, null,
                        errors = ListOfCatDetailErrors(R.string.failed_to_load_the_list1)
                    )
                }

                is CommunicationResult.Exception -> {
                    petDetailUIState.value = UiState(
                        loading = false, null,
                        errors = ListOfCatDetailErrors(R.string.unknown_error1)
                    )
                }

                is CommunicationResult.Success -> {
                    petDetailUIState.value = UiState(
                        loading = false, result.data,
                        errors = null
                    )
                }
            }
        }
    }

    private fun saveImageToGallery(context: Context, inputStream: InputStream, fileName: String) {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        uri?.let {
            resolver.openOutputStream(it).use { outputStream ->
                outputStream?.let { stream ->
                    val buffer = ByteArray(8192)
                    var bytesRead: Int
                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        stream.write(buffer, 0, bytesRead)
                    }
                }
            }
        }
    }
    fun saveImageDownloadState(url: String, downloaded: Boolean) {
        viewModelScope.launch {
            dataStoreRepository.saveImageState(url, downloaded)
        }
    }

    fun isImageDownloaded(url: String): LiveData<Boolean> {
        return liveData {
            emit(dataStoreRepository.isImageDownloaded(url))
        }
    }

    fun downloadImage(context: Context, url: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val client = OkHttpClient()

                val request = Request.Builder().url(url).build()
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) throw IOException(
                        context.getString(
                            R.string.failed_to_download_file,
                            response
                        ))

                    val inputStream = response.body?.byteStream()
                    inputStream?.let { stream ->
                        saveImageToGallery(
                            context,
                            stream,
                            "downloaded_image_${UUID.randomUUID()}.jpg"
                        )
                    }
                }
                saveImageDownloadState(url, true)
            }
        }
    }
}