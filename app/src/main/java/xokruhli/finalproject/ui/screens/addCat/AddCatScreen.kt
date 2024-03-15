package xokruhli.finalproject.ui.screens.addCat

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.example.finalproject.ui.elements.BaseScreen
import com.example.finalproject.ui.elements.PlaceholderScreenContent
import xokruhli.finalproject.model.UiState
import xokruhli.finalproject.R
import xokruhli.finalproject.model.profile.Cat
import xokruhli.finalproject.ui.elements.TextInputField

const val AddCatScreenNavigationTag = "addCatScreenNavigationTag"
const val NameInputTestTag = "nameInputTestTag"
const val ButtonTestTag = "buttonTestTag"

@Destination(start = false)
@Composable
fun AddCatScreen(
    navigator: DestinationsNavigator,
    id: Long? = null
) {

    val viewModel = hiltViewModel<AddCatViewModel>()
    val uiState = viewModel.uiState
    if (id != null) {
        LaunchedEffect(id) {
            viewModel.getFodByID(id)
        }
    }

    BaseScreen(
        topBarText = stringResource(R.string.add_edit_cat),
        drawFullScreenContent = true,
        showLoading = uiState.value.loading,
        placeholderScreenContent = uiState.value.errors?.let { errors ->
            PlaceholderScreenContent(
                image = null,
                text = stringResource(id = errors.communicationError ?: R.string.unknown_error)
            )
        },
        onBackClick = { navigator.popBackStack() },
        navigator = navigator
    ) {
        AddCatScreenContent(
            paddingValues = it,
            uiState = uiState.value,
            navigator = navigator,
            viewModel = viewModel,
        )
    }
}

@Composable
fun AddCatScreenContent(
    paddingValues: PaddingValues,
    uiState: UiState<Cat, AddCatErrors>,
    navigator: DestinationsNavigator,
    viewModel: AddCatViewModel,
) {

    val context = LocalContext.current
    var hasCameraPermission by remember { mutableStateOf(
        ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    )}

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        hasCameraPermission = isGranted
    }
    LaunchedEffect(key1 = true) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    val imageCaptureLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        bitmap?.let {
            viewModel.processImageForText(it)
        }
    }


    var name by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var vaccination by remember { mutableStateOf("") }
    var medicalReport by remember { mutableStateOf("") }
    var photo by remember { mutableStateOf("") }

    LaunchedEffect(uiState.data) {
        name = uiState.data?.name ?: ""
        breed = uiState.data?.breed ?: ""
        weight = uiState.data?.weight?.toString() ?: ""
        height = uiState.data?.height?.toString() ?: ""
        vaccination = uiState.data?.vaccination ?: ""
        medicalReport = uiState.data?.medical_report ?: ""
        photo = uiState.data?.photo ?: "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg"
    }


    var weightError by remember { mutableStateOf<String?>(null) }
    var heightError by remember { mutableStateOf<String?>(null) }

    val isWeightValid = weight.toDoubleOrNull() != null
    val isHeightValid = height.toDoubleOrNull() != null
    val isFormValid = name.isNotBlank() && breed.isNotBlank() && isWeightValid && isHeightValid



    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let { selectedUri ->
                Log.e("ImageError", "Context: $context, SelectedUri: $selectedUri")
                photo = selectedUri.toString() // Directly use the URI as a String
            }
        }
    )




    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        item {
            if (photo.isNotEmpty()) {
                AsyncImage(
                    model = photo ?: "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg",
                    contentDescription = stringResource(R.string.selected_photo),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable {
                            singlePhotoPickerLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    },
                )
            }
            TextInputField(
                value = name,
                hint = stringResource(R.string.name),
                onValueChange = { name = it },
                modifier = Modifier.testTag(NameInputTestTag)
            )
            TextInputField(
                value = breed,
                hint = stringResource(R.string.breed),
                onValueChange = { breed = it }
            )
            TextInputField(
                value = weight,
                hint = stringResource(R.string.weight_kg),
                onValueChange = {
                    weight = it
                    weightError = if (it.toDoubleOrNull() == null && it.isNotBlank()) "Value must be a number" else null
                },
                errorMessage = weightError
            )
            TextInputField(
                value = height,
                hint = stringResource(R.string.height_cm),
                onValueChange = {
                    height = it
                    heightError = if (it.toDoubleOrNull() == null && it.isNotBlank()) "Value must be a number" else null
                },
                errorMessage = heightError
            )
            TextInputField(
                value = vaccination,
                hint = stringResource(R.string.vaccination),
                onValueChange = { vaccination = it }
            )
            if (hasCameraPermission) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            imageCaptureLauncher.launch(null)
                                  },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black
                        ),
                    ) {
                        Icon(imageVector = Icons.Default.Camera, contentDescription = null, modifier = Modifier.padding(end = 4.dp))
                        Text(stringResource(R.string.capture_medical_report))
                    }
                }

                if (viewModel.recognizedText.value.isNotEmpty()) {
                    medicalReport = viewModel.recognizedText.value
                    Text(
                        stringResource(R.string.recognized_text,
                            viewModel.recognizedText.value)
                    )
                }
            } else {
                Text(stringResource(R.string.camera_permission_is_required))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        if (isFormValid) {
                            val newCat = Cat(
                                id = uiState.data?.id,
                                name = name.trim(),
                                breed = breed.trim(),
                                weight = weight.toDoubleOrNull() ?: 0.0,
                                height = height.toDoubleOrNull() ?: 0.0,
                                vaccination = vaccination.trim(),
                                medical_report = medicalReport.trim(),
                                photo = photo
                            )
                            if (uiState.data != null) {
                                viewModel.updateCat(newCat)
                            } else {
                                viewModel.insertCat(newCat)
                            }
                            navigator.popBackStack()
                        }
                    },
                    modifier = Modifier
                        .testTag(ButtonTestTag)
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.surface)
                    ,
                    enabled = isFormValid,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Text(
                        if (uiState.data != null)
                            stringResource(R.string.update_cat)
                        else stringResource(R.string.add_cat)
                    )
                }
            }
        }
    }

}

fun getPathFromUri(context: Context, uri: Uri): String? {
    if (uri.authority.equals("com.android.providers.media.documents", ignoreCase = true)) {
        val docId = DocumentsContract.getDocumentId(uri)
        val split = docId.split(":").toTypedArray()
        val type = split[0]

        var contentUri: Uri? = null
        if ("image" == type) {
            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        } else if ("video" == type) {
            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        } else if ("audio" == type) {
            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        }

        val selection = "_id=?"
        val selectionArgs = arrayOf(split[1])

        return getDataColumn(context, contentUri, selection, selectionArgs)
    }
    return null
}

fun getDataColumn(context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {
    uri?.let { contentUri ->
        context.contentResolver.query(contentUri, arrayOf(MediaStore.Images.Media.DATA), selection, selectionArgs, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                return cursor.getString(columnIndex)
            }
        }
    }
    return null
}
