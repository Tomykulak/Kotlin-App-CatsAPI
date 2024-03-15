package xokruhli.finalproject.ui.elements

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PetDescriptor(
    text: String,
    contentColor: Color = Color.Black,
    titleFontSize: TextUnit = 20.sp,
){
    Row(
        modifier = Modifier
            .padding(6.dp)
    ){
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            color = contentColor,
            fontSize = titleFontSize,
        )
    }
}