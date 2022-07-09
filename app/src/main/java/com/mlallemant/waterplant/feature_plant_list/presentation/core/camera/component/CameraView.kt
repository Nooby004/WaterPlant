package com.mlallemant.waterplant.feature_plant_list.presentation.core.camera.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.FlipCameraAndroid
import androidx.compose.material.icons.sharp.Lens
import androidx.compose.material.icons.sharp.PhotoLibrary
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mlallemant.waterplant.R
import com.mlallemant.waterplant.feature_plant_list.presentation.core.camera.CameraEvent

@Composable
fun CameraControls(cameraEvent: (CameraEvent) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        CameraControl(
            Icons.Sharp.FlipCameraAndroid,
            R.string.icn_camera_view_switch_camera_content_description,
            modifier = Modifier.size(48.dp),
            onClick = { cameraEvent(CameraEvent.OnSwitchCameraClick) }
        )

        CameraControl(
            Icons.Sharp.Lens,
            R.string.icn_camera_view_camera_shutter_content_description,
            modifier = Modifier
                .size(48.dp)
                .padding(1.dp)
                .border(1.dp, Color.White, CircleShape),
            onClick = { cameraEvent(CameraEvent.OnCameraClick) }
        )

        CameraControl(
            Icons.Sharp.PhotoLibrary,
            R.string.icn_camera_view_view_gallery_content_description,
            modifier = Modifier.size(48.dp),
            onClick = { cameraEvent(CameraEvent.OnGalleryViewClick) }
        )

    }
}


@Composable
fun CameraControl(
    imageVector: ImageVector,
    contentDescId: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {


    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector,
            contentDescription = stringResource(id = contentDescId),
            modifier = modifier,
            tint = Color.White
        )
    }

}