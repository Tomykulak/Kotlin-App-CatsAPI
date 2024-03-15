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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import xokruhli.finalproject.R
import xokruhli.finalproject.model.catApi.Breed


@Composable
fun RandomCatCard(
    breed: Breed,
    onClick: (() -> Unit)
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(Color.White)
            .border(
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, Color.Black)
            )
            .clip(RoundedCornerShape(10.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            BreedImage(breed = breed)
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = breed.name ?: stringResource(id = R.string.unknown_pet),
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            BreedAttribute(
                label = stringResource(id = R.string.origin),
                value = breed.origin ?: stringResource(R.string.unknown),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            BreedAttribute(
                label = stringResource(R.string.temperament),
                value = breed.temperament ?: stringResource(R.string.unknown),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.End),
                tint = Color.Black
            )
        }
    }
}

@Composable
fun BreedImage(breed: Breed) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        AsyncImage(
            model = breed.image?.url ?: "https://www.shutterstock.com/shutterstock/photos/2274595907/display_1500/stock-vector-template-doll-toy-white-feminism-design-logo-icon-isolated-background-vector-hello-kitty-art-2274595907.jpg",
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun BreedAttribute(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "$label: $value",
        color = Color.Gray,
        fontSize = 14.sp,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}

