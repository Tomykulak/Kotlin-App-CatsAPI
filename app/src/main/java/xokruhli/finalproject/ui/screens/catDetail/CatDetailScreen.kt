package xokruhli.finalproject.ui.screens.catDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.finalproject.ui.elements.BaseScreen
import com.example.finalproject.ui.elements.PlaceholderScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import xokruhli.finalproject.model.UiState
import xokruhli.finalproject.R
import xokruhli.finalproject.model.catApi.Breed
import xokruhli.finalproject.ui.elements.PetDescriptor
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue


@Destination
@Composable
fun CatDetailScreen(
    navigator: DestinationsNavigator,
    id: String,
    url: String?
){
    val viewModel = hiltViewModel<PetDetailScreenViewModel>()
    val uiState: MutableState<UiState<Breed, ListOfCatDetailErrors>> = rememberSaveable {
        mutableStateOf(UiState())
    }
    viewModel.let{
        LaunchedEffect(it){
            it.loadPet(id)
        }
    }
    viewModel.petDetailUIState.value.let {
        uiState.value = it
    }

    BaseScreen(
        topBarText = stringResource(R.string.detail),
        showLoading = uiState.value.loading,
        onBackClick = { navigator.popBackStack() },
        placeholderScreenContent =
        if (uiState.value.errors != null)
            PlaceholderScreenContent(
                image = null,
                text = stringResource(id = uiState.value.errors!!.communicationError)
            )
        else null,
        navigator = navigator
    ) {
        viewModel.petDetailUIState.value.data?.let { pet ->
            CatDetailScreenContent(
                cat = pet,
                uiState = uiState,
                url = url ?: "",
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun CatDetailScreenContent(
    cat: Breed,
    uiState: MutableState<UiState<Breed, ListOfCatDetailErrors>>,
    url: String?,
    viewModel: PetDetailScreenViewModel?
) {
    val context = LocalContext.current
    val isImageDownloadedInViewmodel =
        viewModel!!.isImageDownloaded(url!!)
            .observeAsState(false).value

    var isImageDownloaded by remember {
        mutableStateOf(isImageDownloadedInViewmodel)
    }
    LaunchedEffect(isImageDownloaded) {
        isImageDownloaded = isImageDownloadedInViewmodel
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(10.dp)
                    .clip(
                        RoundedCornerShape(
                            topEnd = 10.dp,
                            topStart = 10.dp,
                            bottomEnd = 10.dp,
                            bottomStart = 10.dp
                        )
                    )
            ) {
                BreedImage(url = url)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                uiState.value.data?.name?.takeIf {
                    it.isNotEmpty()
                } ?: stringResource(R.string.cat_name_doesn_t_exist),
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                color = Color.Black,
                modifier = Modifier.padding(6.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            PetDescriptor(
                text = cat.description.toString()
            )

            Spacer(modifier = Modifier.height(8.dp))

            PetDescriptor(
                text = stringResource(R.string.cat_lifespan) + cat.lifeSpan.toString() + stringResource(
                    R.string.years
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            PetDescriptor(
                text = stringResource(R.string.origin) + cat.origin.toString()
            )

            Spacer(modifier = Modifier.height(8.dp))

            PetDescriptor(
                text = stringResource(R.string.adaptability) + cat.adaptability?.let { "⭐".repeat(it) } ?: ""
            )

            Spacer(modifier = Modifier.height(8.dp))

            PetDescriptor(
                text = stringResource(R.string.child_friendly) + cat.childFriendly?.let { "⭐".repeat(it) } ?: ""
            )

            Spacer(modifier = Modifier.height(8.dp))

            PetDescriptor(
                text = stringResource(R.string.dog_friendly) + cat.dogFriendly?.let { "⭐".repeat(it) } ?: ""
            )

            Spacer(modifier = Modifier.height(8.dp))

            PetDescriptor(
                text = stringResource(R.string.energy_level) + cat.energyLevel?.let { "⭐".repeat(it) } ?: ""
            )

            Spacer(modifier = Modifier.height(8.dp))

            PetDescriptor(
                text = stringResource(R.string.grooming) + cat.grooming?.let { "⭐".repeat(it) } ?: ""
            )
            val downloadIcon = if (isImageDownloaded) Icons.Filled.DownloadDone else Icons.Filled.Download

            IconButton(onClick = {
                if (!isImageDownloaded) {
                    viewModel.downloadImage(context, url)
                    isImageDownloaded = true
                }
            }) {
                Icon(
                    downloadIcon,
                    contentDescription = ""
                )
            }
        }
    }
}




@Composable
fun BreedImage(url: String?) {
    var imageUrl = url
    if(url!!.isEmpty()){
        imageUrl = "https://www.shutterstock.com/shutterstock/photos/2274595907/display_1500/stock-vector-template-doll-toy-white-feminism-design-logo-icon-isolated-background-vector-hello-kitty-art-2274595907.jpg"
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}