package com.mlallemant.waterplant.feature_plant_list.presentation.add_edit_plant.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mlallemant.waterplant.feature_plant_list.presentation.add_edit_plant.AddEditPlantEvent
import com.mlallemant.waterplant.feature_plant_list.presentation.core.camera.component.CameraView

@Composable
fun PlantImageView(
    picturePath: String = "",
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
                AddEditPlantEvent.TakePicture
            }


    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            /*Icon(
                imageVector = Icons.Default.AddAPhoto,
                contentDescription = "Add plant photo",
                tint = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.scale(2f)
            )*/

            CameraView(onImageCaptured = { uri, fromGallery ->
                Log.d(TAG, "Image Uri Captured from Camera View")
//Todo : use the uri as needed

            }, onError = { imageCaptureException ->

            })
        }


    }
}