package xokruhli.finalproject.ui.screens.profileCat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.finalproject.ui.elements.BaseScreen
import com.example.finalproject.ui.elements.PlaceholderScreenContent
import xokruhli.finalproject.R
import xokruhli.finalproject.ui.elements.CatDetailCard
import xokruhli.finalproject.model.UiState
import xokruhli.finalproject.model.profile.Cat
import xokruhli.finalproject.ui.screens.destinations.AddCatScreenDestination

@Destination(start = false)
@Composable
fun ProfileCatScreen(
    navigator: DestinationsNavigator,
    id: Long
) {
    val viewModel = hiltViewModel<ProfileCatViewModel>()
    viewModel.let{
        LaunchedEffect(it){
            it.loadData(id)
        }
    }
    val uiState: MutableState<UiState<Cat, ProfileCatErrors>> = rememberSaveable {
        mutableStateOf(UiState())
    }

    viewModel.uiState.value.let {
        uiState.value = it
    }

    BaseScreen(
        topBarText = stringResource(R.string.cat_detail),
        drawFullScreenContent = true,
        showLoading = uiState.value.loading,
        actions = {
            Row {
                IconButton(
                    onClick = {
                        navigator.navigate(AddCatScreenDestination(id))
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = ""
                    )
                }

                IconButton(
                    onClick = {
                        viewModel.deleteCat(viewModel.uiState.value.data!!)
                        navigator.popBackStack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = ""
                    )
                }
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
        ProfileCatScreenContent(
            paddingValues = it,
            uiState = uiState.value,
            navigator = navigator,
            viewModel = viewModel,
            id = id
        )
    }
}

@Composable
fun ProfileCatScreenContent(
    paddingValues: PaddingValues,
    uiState: UiState<Cat, ProfileCatErrors>,
    navigator: DestinationsNavigator,
    viewModel: ProfileCatViewModel,
    id: Long,
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues)
    ) {
        item {
            if (uiState.data != null) {
                CatDetailCard(cat = uiState.data)
            } else {
                Text(stringResource(R.string.no_cat_detail))
            }
        }
    }
}