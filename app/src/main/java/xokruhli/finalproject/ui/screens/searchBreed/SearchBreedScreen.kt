package xokruhli.finalproject.ui.screens.searchBreed

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.finalproject.ui.elements.BaseScreen
import com.example.finalproject.ui.elements.PlaceholderScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import xokruhli.finalproject.R
import xokruhli.finalproject.model.UiState
import xokruhli.finalproject.model.catApi.Breed
import xokruhli.finalproject.ui.elements.PetRow
import xokruhli.finalproject.ui.elements.SearchBox
import xokruhli.finalproject.ui.screens.destinations.CatDetailScreenDestination

@Destination
@Composable
fun SearchBreedScreen(
    navigator: DestinationsNavigator
){
    val viewModel = hiltViewModel<SearchBreedViewModel>()
    val uiState: MutableState<UiState<List<Breed>, SearchBreedErrors>>
            = rememberSaveable{ mutableStateOf(UiState()) }

    viewModel.petsUIState.value.let {
        uiState.value = it
    }

    BaseScreen(
        topBarText = stringResource(R.string.search_breed),
        drawFullScreenContent = true,
        showLoading = uiState.value.loading,
        placeholderScreenContent =
        if (uiState.value.errors != null)
            PlaceholderScreenContent(null, stringResource(id = uiState.value.errors!!.communicationError))
        else
            null,
        actions = {
            useSearchBox(
                viewModel = viewModel
            )
        },
        navigator = navigator
    ) {
        SearchBreedScreenContent(
            paddingValues = it,
            uiState = uiState.value,
            navigator = navigator,
            viewModel = viewModel
        )
    }
}

@Composable
fun useSearchBox(
    viewModel: SearchBreedViewModel
){
    var filterBreedText by remember {
        mutableStateOf("")
    }
    SearchBox(
        value = filterBreedText,
        hint = stringResource(R.string.filter_by_breed),
        onValueChange = {
            filterBreedText = it
            viewModel.findBreedByName(it)
        },
        trailingIcon = {
            if (filterBreedText.isNotEmpty()) {
                IconButton(
                    onClick = {
                        filterBreedText = ""
                        viewModel.findBreedByName(filterBreedText)
                    }
                ) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                }
            }
        }
    )
}

@Composable
fun SearchBreedScreenContent(
    paddingValues: PaddingValues,
    uiState: UiState<List<Breed>, SearchBreedErrors>,
    navigator: DestinationsNavigator,
    viewModel: SearchBreedViewModel
){
    LazyColumn(modifier = Modifier.padding(paddingValues = paddingValues)) {
        if (uiState.data != null) {
            val data = uiState.data!!
            data.forEach { cat ->
                item {
                    PetRow(
                        cat = cat
                    ) {
                        navigator.navigate(CatDetailScreenDestination(cat.id!!, cat.image?.url))
                    }
                }
            }
        }
        if (viewModel.isSearchResultEmpty.value) {
            item {
                Text(
                    text = stringResource(
                        xokruhli.finalproject.R.string.no_cats_found_sorry
                    ),
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