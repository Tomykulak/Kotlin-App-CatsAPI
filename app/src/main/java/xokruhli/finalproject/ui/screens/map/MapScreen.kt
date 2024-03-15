package xokruhli.finalproject.ui.screens.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.finalproject.ui.elements.BaseScreen
import com.example.finalproject.ui.elements.PlaceholderScreenContent
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import xokruhli.finalproject.model.UiState
import kotlinx.coroutines.launch
import xokruhli.finalproject.R
import xokruhli.finalproject.model.googlePlacesApi.Place
import xokruhli.finalproject.model.googlePlacesApi.Result


const val MapScreenTag = "mapScreenTag"
const val MapExists = "mapExists"
const val MarkerTag = "markerTag"

@Destination(start = false)
@Composable
fun MapScreen(
    navigator: DestinationsNavigator
) {
    val viewModel = hiltViewModel<MapViewModel>()

    val uiState: MutableState<UiState<Place, MapError>> = rememberSaveable {
        mutableStateOf(UiState())
    }

    viewModel.uiState.value.let {
        uiState.value = it
    }

    BaseScreen(
        topBarText = stringResource(R.string.cat_clinics),
        drawFullScreenContent = true,
        showLoading = uiState.value.loading,
        placeholderScreenContent =
        if (uiState.value.errors != null)
            PlaceholderScreenContent(
                image = null,
                text = stringResource(id = uiState.value.errors!!.communicationError ?: 0)
            )
        else null,
        onBackClick = null,
        navigator = navigator

    ) {
        MapScreenContent(
            paddingValues = it,
            uiState = uiState.value,
            navigator = navigator,
            tag = stringResource(R.string.googlemap)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreenContent(
    paddingValues: PaddingValues,
    uiState: UiState<Place, MapError>,
    navigator: DestinationsNavigator,
    tag: String
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    var selectedResult by remember {
        mutableStateOf<Result?>(null)
    }


    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                zoomControlsEnabled = false,
                mapToolbarEnabled = false
            )
        )
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(49.19791912666906, 16.609545570402467), 12.5f
        )
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .padding(bottom = 180.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = selectedResult?.icon ?: "",
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    contentScale = ContentScale.Crop
                )
                if(selectedResult?.user_ratings_total != null){
                    Text(text = stringResource(R.string.ratings) + selectedResult?.user_ratings_total.toString() + "★")
                }
                Text(text = selectedResult?.name ?: stringResource(R.string.no_result_selected))
                Text(text = if (selectedResult?.opening_hours?.open_now == true) stringResource(R.string.open) else stringResource(
                    R.string.closed
                )
                )
                Text(text = selectedResult?.vicinity ?: "")
                Text(text = if(selectedResult?.permanently_closed == true) stringResource(
                    R.string.closed
                ) else "", color = Color.Red)


            }
        },
        sheetPeekHeight = 0.dp
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(MapExists)
            ) {
                GoogleMap(
                    modifier = Modifier
                        .fillMaxHeight()
                        .testTag(MarkerTag),
                    uiSettings = mapUiSettings,
                    cameraPositionState = cameraPositionState
                ) {
                    var selectedMarker: Marker? by remember { mutableStateOf(null) }

                    uiState.data?.results?.forEach {
                        val markerState = MarkerState(
                            LatLng(
                                it.geometry?.location!!.lat,
                                it.geometry.location.lng
                            )
                        )
                        Marker(
                            state = markerState,
                            onClick = { m ->
                                scope.launch {
                                    scaffoldState.bottomSheetState.expand()
                                    selectedMarker?.setIcon(BitmapDescriptorFactory.defaultMarker())
                                    m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                                    selectedMarker = m
                                }
                                selectedResult = it
                                true
                            },
                        )
                    }
                }

            }
        }
    }
}