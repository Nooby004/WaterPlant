package com.mlallemant.waterplant.feature_plant_list.presentation.core.permission

sealed class PermissionEvent {

    object OnPermissionGranted : PermissionEvent()

    object OnPermissionDenied : PermissionEvent()

}