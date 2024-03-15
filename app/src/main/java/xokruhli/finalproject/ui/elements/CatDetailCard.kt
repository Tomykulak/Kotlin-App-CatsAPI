package xokruhli.finalproject.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Vaccines
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import xokruhli.finalproject.R
import xokruhli.finalproject.model.profile.Cat

@Composable
fun CatDetailCard(cat: Cat?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            if (cat?.photo != null) {
                AsyncImage(
                    model = cat.photo,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(1.5f)
                        .clip(RoundedCornerShape(20.dp))
                )
            } else {
                AsyncImage(
                    model = R.mipmap.cat_placeholder,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(1.5f)
                        .clip(RoundedCornerShape(20.dp))
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (cat != null) {
                DetailItem(
                    title = stringResource(id = R.string.name),
                    value = cat.name ?: stringResource(R.string.no_information),
                )
                DetailItem(
                    title = stringResource(id = R.string.breed),
                    value = cat.breed ?: stringResource(R.string.no_information),
                )
                DetailItem(
                    title = stringResource(R.string.weight),
                    value = (cat.weight?.toString() + " kg") ?: stringResource(R.string.no_information),
                )
                DetailItem(
                    title = stringResource(R.string.height),
                    value = (cat.height?.toString() + " cm") ?: stringResource(R.string.no_information),
                )
                DetailItem(
                    title = stringResource(id = R.string.vaccination),
                    value = cat.vaccination?.toString() ?: stringResource(R.string.no_information),
                )
                DetailItem(title = stringResource(R.string.medical_report),
                    value = cat.medical_report?.toString() ?: stringResource(R.string.no_information),
                )
            } else {
                Text(
                    text = stringResource(R.string.no_cat_information_available),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun DetailItem(
    title: String,
    value: String,
    icon: ImageVector? = null
) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    }
}

