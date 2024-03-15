package com.example.finalproject.ui.elements

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalConvenienceStore
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import xokruhli.finalproject.R
import xokruhli.finalproject.ui.screens.destinations.ListOfCatsScreenDestination
import xokruhli.finalproject.ui.screens.destinations.MapScreenDestination
import xokruhli.finalproject.ui.screens.destinations.ProfileScreenDestination
import xokruhli.finalproject.ui.screens.destinations.SearchBreedScreenDestination
import xokruhli.finalproject.ui.theme.basicMargin
import xokruhli.finalproject.ui.theme.basicTextColor
import xokruhli.finalproject.ui.theme.getTintColor

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BaseScreen(
    topBarText: String?,
    onBackClick: (() -> Unit)? = null,
    showSidePadding: Boolean = true,
    drawFullScreenContent: Boolean = false,
    placeholderScreenContent: PlaceholderScreenContent? = null,
    showLoading: Boolean = false,
    navigator: DestinationsNavigator? = null,
    floatingActionButton: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (paddingValues: PaddingValues) -> Unit) {

    Scaffold(
        contentColor = Color.Black,
        containerColor = Color.White,
        floatingActionButton = floatingActionButton,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.BlueLight),
                ),
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally)
                    ) {
                        if(topBarText != null) {
                            Text(
                                text = topBarText,
                                style = MaterialTheme.typography.bodyLarge,
                                color = basicTextColor(),
                                modifier = Modifier
                                    .padding(start = 0.dp)
                                    .weight(5.5f)
                            )
                        }
                    }
                },
                actions = actions,
                navigationIcon = {
                    if (onBackClick != null) {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back),
                                tint = getTintColor()
                            )
                        }
                    }
                }
            )
        }, bottomBar = {
            Box(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ){
                if(navigator != null){
                    BottomAppBar (
                        containerColor = colorResource(
                            id = R.color.BlueLight
                        ),
                        contentColor = basicTextColor(),
                        content = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 0.dp,
                                        end = 0.dp
                                    ),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                IconButton(
                                    onClick = {
                                        navigator.navigate(ListOfCatsScreenDestination)
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Home,
                                        contentDescription = ""
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        navigator.navigate(SearchBreedScreenDestination)
                                    }
                                ){
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = ""
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        navigator.navigate(MapScreenDestination)
                                    },
                                    modifier = Modifier.testTag("")
                                ){
                                    Icon(
                                        imageVector = Icons.Default.LocalHospital,
                                        contentDescription = ""
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        navigator.navigate(ProfileScreenDestination)
                                    },
                                    modifier = Modifier.testTag("")
                                ){
                                    Icon(
                                        imageVector = Icons.Default.Pets,
                                        contentDescription = ""
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    ) {
        if (placeholderScreenContent != null){
            PlaceHolderScreen(
                modifier = Modifier.padding(it),
                content = placeholderScreenContent)
        } else if (showLoading){
            LoadingScreen(modifier = Modifier.padding(it))
        } else {
            if (!drawFullScreenContent) {
                LazyColumn(modifier = Modifier.padding(it)) {
                    item {
                        Column(
                            verticalArrangement = Arrangement.Top,
                            modifier = Modifier
                                .padding(if (!showSidePadding) basicMargin() else 0.dp)
                        ) {
                            content(it)
                        }
                    }
                }
            } else {
                content(it)
            }
        }
    }

}
