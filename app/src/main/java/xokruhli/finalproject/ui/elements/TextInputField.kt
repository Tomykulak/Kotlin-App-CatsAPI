@file:OptIn(ExperimentalMaterial3Api::class)

package xokruhli.finalproject.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xokruhli.finalproject.R


@Composable
fun TextInputField(
    value: String,
    hint: String,
    leadingIcon: Int? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Ascii,
    onValueChange: (String) -> Unit,
    errorMessage: String? = null,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
    modifier: Modifier? = null
) {
    val passwordVisible = rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = hint) },
            visualTransformation = if (keyboardType == KeyboardType.Password) {
                if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            isError = errorMessage != null,
            maxLines = 1,
            trailingIcon = getTrailingIcon(trailingIcon, keyboardType, passwordVisible),
            leadingIcon = leadingIcon?.let {
                { Icon(painter = painterResource(id = it), contentDescription = null) }
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = 5.dp),
            colors = getTextFieldColors(colors, errorMessage),
            enabled = enabled,
            readOnly = readOnly
        )

        if (!errorMessage.isNullOrEmpty()) {
            Text(
                text = errorMessage,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                color = Red,
                textAlign = TextAlign.Start,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
private fun getTrailingIcon(
    trailingIcon: @Composable (() -> Unit)?,
    keyboardType: KeyboardType,
    passwordVisible: MutableState<Boolean>
): @Composable (() -> Unit) {
    return {
        if (trailingIcon == null) {
            if (keyboardType == KeyboardType.Password) {
                val image = if (passwordVisible.value)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                val description = if (passwordVisible.value) stringResource(R.string.hide_password) else stringResource(R.string.show_password)
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(
                        imageVector = image,
                        contentDescription = description,
                        tint = Color.Black
                    )
                }
            }
        } else {
            trailingIcon()
        }
    }
}
@Composable
private fun getTextFieldColors(
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
    errorMessage: String?
): TextFieldColors {
    return if (colors == TextFieldDefaults.outlinedTextFieldColors()) {
        TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = DarkGray,
            focusedBorderColor = if (!errorMessage.isNullOrEmpty()) Red else DarkGray,
            unfocusedBorderColor = if (!errorMessage.isNullOrEmpty()) Red else Gray,
            focusedLabelColor = if (!errorMessage.isNullOrEmpty()) Red else Gray,
        )
    } else {
        colors
    }
}