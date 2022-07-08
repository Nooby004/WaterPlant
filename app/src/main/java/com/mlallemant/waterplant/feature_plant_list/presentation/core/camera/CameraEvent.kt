package com.mlallemant.waterplant.feature_plant_list.presentation.core.camera

sealed class CameraEvent {

    object OnCameraClick : CameraEvent()
    object OnGalleryViewClick : CameraEvent()
    object OnSwitchCameraClick : CameraEvent()
}