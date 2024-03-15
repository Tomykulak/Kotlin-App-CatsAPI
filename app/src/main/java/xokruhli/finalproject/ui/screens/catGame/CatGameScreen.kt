package xokruhli.finalproject.ui.screens.catGame

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.finalproject.ui.elements.BaseScreen
import com.example.finalproject.ui.elements.PlaceholderScreenContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import xokruhli.finalproject.R
import xokruhli.finalproject.model.UiState
import xokruhli.finalproject.model.catApi.Breed

@Destination
@Composable
fun CatGameScreen(
    navigator: DestinationsNavigator
){
    val viewModel = hiltViewModel<CatGameViewModel>()
    val uiState: MutableState<UiState<List<Breed>, ListOfCatGameErrors>>
            = rememberSaveable{ mutableStateOf(UiState()) }

    viewModel.petsUIState.value.let {
        uiState.value = it
    }

    BaseScreen(
        topBarText = stringResource(R.string.game),
        drawFullScreenContent = true,
        showLoading = uiState.value.loading,
        placeholderScreenContent =
        if (uiState.value.errors != null)
            PlaceholderScreenContent(null, stringResource(id = uiState.value.errors!!.communicationError))
        else
            null,
        onBackClick = { navigator.popBackStack() },
    ) {
        CatGameScreenContent(
            paddingValues = it,
            uiState = uiState.value,
            navigator = navigator,
            viewModel = viewModel
        )
    }
}

@Composable
fun CatGameScreenContent(
    paddingValues: PaddingValues,
    uiState: UiState<List<Breed>, ListOfCatGameErrors>,
    navigator: DestinationsNavigator,
    viewModel: CatGameViewModel
){
    Column(
        modifier = Modifier
        .padding(8.dp)
    ){
        Button(onClick = { /* TODO CHECK WIN */}) {
            Text("Option")
        }
    }
}