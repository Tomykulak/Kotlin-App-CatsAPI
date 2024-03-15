package xokruhli.finalproject.ui.elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import xokruhli.finalproject.R
import xokruhli.finalproject.model.profile.Cat


@Composable
fun CatRow(
    cat: Cat,
    onClick: (() -> Unit)
) {
    Column(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick() }
                .background(Color.White)
                .border(
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(2.dp, Color.Black)
                )
                .clip(RoundedCornerShape(10.dp))
        ) {
            Row(
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                var image: Any = R.mipmap.cat_placeholder

                if(cat.photo != null){
                    image = cat.photo
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.weight(1f) // Gives maximum available space after accounting for the icon's space
                ) {
                    Box(
                        modifier = Modifier
                            .size(92.dp)
                            .padding(4.dp)
                            .clip(shape = RoundedCornerShape(8.dp))
                    ) {
                        CatImage(cat = cat)
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = cat.name?: stringResource(R.string.unknown_pet),
                        color = Color.Black,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,  // Limits the text to one line
                        overflow = TextOverflow.Ellipsis,  // Adds ellipsis if the text is too long
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)  // Give it a flexible weight and some padding to the right
                    )
                }

                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp),
                    tint = Color.Black
                )
            }
        }
    }
}


@Composable
fun CatImage(cat: Cat) {
    Box(
        modifier = Modifier
            .size(92.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        AsyncImage(
            model = cat.photo ?: "https://www.shutterstock.com/shutterstock/photos/2274595907/display_1500/stock-vector-template-doll-toy-white-feminism-design-logo-icon-isolated-background-vector-hello-kitty-art-2274595907.jpg",
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}