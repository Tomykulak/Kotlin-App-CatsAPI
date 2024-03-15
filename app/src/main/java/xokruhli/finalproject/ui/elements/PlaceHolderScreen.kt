package com.example.finalproject.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import xokruhli.finalproject.ui.theme.basicMargin
import xokruhli.finalproject.ui.theme.basicTextColor
import xokruhli.finalproject.ui.theme.basicTextStyle

data class PlaceholderScreenContent(val image: Int?,
                                    val text: String?)

@Composable
fun PlaceHolderScreen(
    modifier: Modifier,
    content: PlaceholderScreenContent){
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(basicMargin())) {

            if (content.image != null) {
                Image(
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.width(300.dp),
                    painter = painterResource(id = content.image),
                    contentDescription = null)
            }
            Spacer(modifier = Modifier.height(basicMargin()))

            if (content.text != null){
                Text(text = content.text,
                    style = basicTextStyle(),
                    textAlign = TextAlign.Center,
                    color = basicTextColor())
            }
        }
    }
}