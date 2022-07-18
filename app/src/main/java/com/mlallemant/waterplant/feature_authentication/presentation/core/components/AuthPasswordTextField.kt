package com.mlallemant.waterplant.feature_authentication.presentation.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun AuthPasswordTextField(
    hint: String,
    value: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Password,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
) {

    val focusManager = LocalFocusManager.current

    var passwordVisibility by rememberSaveable { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = keyboardType
        ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = Color.Gray,
            disabledLabelColor = Color.Transparent,
            focusedIndicatorColor = Color.Gray,
            unfocusedIndicatorColor = Color.Gray
        ),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
        }),
        modifier = modifier
            .fillMaxWidth(0.7f),
        textStyle = TextStyle(color = MaterialTheme.colors.background),
        label = { Text(hint, color = Color.Gray) },
        visualTransformation = if (keyboardType != KeyboardType.Password || passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            if (keyboardType == KeyboardType.Password) {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Show password",
                    )
                }
            }
        },
    )
}