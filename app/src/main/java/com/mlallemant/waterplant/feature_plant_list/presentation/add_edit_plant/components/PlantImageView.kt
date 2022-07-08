package com.mlallemant.waterplant.feature_plant_list.presentation.add_edit_plant.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp

@Composable
fun PlantImageView(
    picturePath: String = "",
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Box(
        modifier = Modifier
            .border(
                BorderStroke(6.dp, color = MaterialTheme.colors.primaryVariant),
                shape = RoundedCornerShape(20.dp)
            )
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable {
                onClick()
            }


    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.AddAPhoto,
                contentDescription = "Add plant photo",
                tint = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.scale(2f)
            )

            /*        CameraView(onImageCaptured = { uri, fromGallery ->
                        Log.d(TAG, "Image Uri Captured from Camera View")
        //Todo : use the uri as needed

                    }, onError = { imageCaptureException ->

                    })*/
        }


    }
}