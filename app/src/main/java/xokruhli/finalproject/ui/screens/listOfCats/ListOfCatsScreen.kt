package xokruhli.finalproject.ui.screens.listOfCats

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import xokruhli.finalproject.ui.elements.RandomCatCard
import xokruhli.finalproject.ui.screens.destinations.CatDetailScreenDestination

@Destination(start = true)
@Composable
fun ListOfCatsScreen(
    navigator: DestinationsNavigator
){
    val viewModel = hiltViewModel<ListOfCatsViewModel>()
    val uiState: MutableState<UiState<List<Breed>, ListOfCatsErrors>>
            = rememberSaveable{ mutableStateOf(UiState()) }

    viewModel.petsUIState.value.let {
        uiState.value = it
    }

    BaseScreen(
        topBarText = stringResource(R.string.random_cat),
        drawFullScreenContent = true,
        showLoading = uiState.value.loading,
        placeholderScreenContent =
        if (uiState.value.errors != null)
            PlaceholderScreenContent(null, stringResource(id = uiState.value.errors!!.communicationError))
        else
            null
        ,
        navigator = navigator
    ) {
        ListOfCatsScreenContent(
            paddingValues = it,
            uiState = uiState.value,
            navigator = navigator,
            viewModel = viewModel
        )
    }
}


@Composable
fun ListOfCatsScreenContent(
    paddingValues: PaddingValues,
    uiState: UiState<List<Breed>, ListOfCatsErrors>,
    navigator: DestinationsNavigator,
    viewModel: ListOfCatsViewModel
){
    LazyColumn(modifier = Modifier.padding(paddingValues = paddingValues)) {
        if (uiState.data != null) {
            val randomCat = uiState.data!!.random()
            item {
                RandomCatCard(
                    breed = randomCat,
                    onClick = { navigator.navigate(CatDetailScreenDestination(randomCat.id!!, randomCat.image?.url)) }
                )
            }
            item {
                Button(
                    onClick = {
                        viewModel.loadCats()
                    },
                    modifier = Modifier.padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Gamepad,
                        contentDescription = ""
                    )
                    Text(stringResource(R.string.randomize_cat))
                }
            }
        }
    }
}
