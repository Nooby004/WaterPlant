package com.mlallemant.waterplant.feature_plant_list.presentation.core.navcontroller.extension

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun <T> NavController.GetOnceResult(keyResult: String, onResult: (T) -> Unit) {
    val valueScreenResult = currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<T>(keyResult)

    valueScreenResult?.value?.let {
        onResult(it)

        currentBackStackEntry
            ?.savedStateHandle
            ?.remove<T>(keyResult)
    }
}