package com.mlallemant.waterplant.feature_plant_list.presentation.plants.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddItem(
    onAdd: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {


        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.clickable {
                onAdd()
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.primaryVariant)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    text = "Ajouter une plante",
                    color = MaterialTheme.colors.background,
                    style = MaterialTheme.typography.h4
                )

                Spacer(modifier = Modifier.width(40.dp))
                
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add plant",
                    tint = MaterialTheme.colors.background,
                    modifier = Modifier.aspectRatio(4f)
                )

            }

        }
    }

}


