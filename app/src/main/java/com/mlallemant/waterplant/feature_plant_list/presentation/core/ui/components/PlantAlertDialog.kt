package com.mlallemant.waterplant.feature_plant_list.presentation.core.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PlantAlertDialog(
    title: String,
    content: String,
    onDismiss: () -> Unit,
    onPositiveClick: () -> Unit,
    onNegativeClick: () -> Unit,
) {

    androidx.compose.material.AlertDialog(title = {
        Text(
            title,
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.primary
        )
        Spacer(modifier = Modifier.height(50.dp))
    }, text = {
        Text(
            content,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.primary
        )

    }, onDismissRequest = {
        onDismiss()
    },
        confirmButton = {
            TextButton(onClick = {
                onPositiveClick()
            }) {
                Text(
                    "OUI",
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.button
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onNegativeClick() }) {
                Text(
                    "NON",
                    color = MaterialTheme.colors.primaryVariant,
                    style = MaterialTheme.typography.button
                )
            }
        },
        backgroundColor = MaterialTheme.colors.background
    )

}