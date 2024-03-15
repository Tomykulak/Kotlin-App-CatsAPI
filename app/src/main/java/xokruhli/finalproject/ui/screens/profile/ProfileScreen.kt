package xokruhli.finalproject.ui.screens.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.finalproject.ui.elements.BaseScreen
import com.example.finalproject.ui.elements.PlaceholderScreenContent
import xokruhli.finalproject.R
import xokruhli.finalproject.model.UiState
import xokruhli.finalproject.model.profile.Cat
import xokruhli.finalproject.ui.elements.CatRow
import xokruhli.finalproject.ui.screens.addCat.AddCatScreenNavigationTag
import xokruhli.finalproject.ui.screens.destinations.AddCatScreenDestination
import xokruhli.finalproject.ui.screens.destinations.ProfileCatScreenDestination

const val ProfileScreenTag = "profileScreenTag"
const val ProfileScreenTagSec = "profileScreenTagSec"

@Destination(start = false)
@Composable
fun ProfileScreen(
    navigator: DestinationsNavigator
) {
    val viewModel = hiltViewModel<ProfileViewModel>()

    val uiState: MutableState<UiState<List<Cat>, ProfileError>> = rememberSaveable {
        mutableStateOf(UiState())
    }

    viewModel.uiState.value.let {
        uiState.value = it
    }

    BaseScreen(
        topBarText = stringResource(R.string.my_cats),
        drawFullScreenContent = true,
        showLoading = false,
        floatingActionButton = {
            FloatingActionButton(onClick = { navigator.navigate(AddCatScreenDestination(null)) }, Modifier.testTag(
                AddCatScreenNavigationTag
            )) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(id = R.string.add_cat))
            }
        },
        placeholderScreenContent =
        if (uiState.value.errors != null)
            PlaceholderScreenContent(
                image = null,
                text = stringResource(id = uiState.value.errors!!.communicationError ?: 0)
            )
        else null,
        onBackClick = { navigator.popBackStack() },
        navigator = navigator
    ) {
        ProfileScreenContent(
            paddingValues = it,
            uiState = uiState.value,
            navigator = navigator,
            viewModel = viewModel,

        )
    }
}

@Composable
fun ProfileScreenContent(
    paddingValues: PaddingValues,
    uiState: UiState<List<Cat>, ProfileError>,
    navigator: DestinationsNavigator,
    viewModel: ProfileViewModel,
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .testTag(ProfileScreenTagSec)
    ) {
        if (uiState.data != null && uiState.data!!.isNotEmpty()) {
            uiState.data!!.forEach { cat ->
                item {
                    CatRow(
                        cat = cat,
                        onClick = {
                            navigator.navigate(
                                ProfileCatScreenDestination(
                                    id = cat.id ?: return@CatRow
                                )
                            )
                        }
                    )
                }
            }
        } else {
            item {
                Text(
                    text = stringResource(R.string.add_more_cats_using_the_button),
                    modifier = Modifier
                        .padding(16.dp)
                        .testTag("noCatsText"),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                )
            }
        }
    }
}


