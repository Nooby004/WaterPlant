package com.mlallemant.waterplant.feature_plant_list.presentation.plants.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun AddItem(
    onAdd: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.Center,
            Alignment.CenterHorizontally
        ) {

            Card(
                elevation = 4.dp,
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .clickable { onAdd() }
                    .clip(shape = CircleShape)
                    .aspectRatio(1f)
                    .border(
                        2.dp,
                        color = MaterialTheme.colors.primaryVariant,
                        CircleShape
                    )
                    .weight(0.8f),
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add plant",
                    tint = MaterialTheme.colors.background,
                    modifier = Modifier.aspectRatio(4f)
                )

            }

            Text(
                text = "",
                color = MaterialTheme.colors.primaryVariant,
                style = MaterialTheme.typography.h6
            )
        }


    }
}



