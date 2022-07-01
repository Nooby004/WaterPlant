package com.mlallemant.waterplant.feature_plant_list.presentation.plants.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mlallemant.waterplant.feature_plant_list.domain.model.Plant

@Composable
fun PlantItem(
    plant: Plant,
    bitmap: Bitmap,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(end = 32.dp)
        ) {
            Text(
                text = plant.name,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.surface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Last photo of the plant"
            )
        }


    }

}

