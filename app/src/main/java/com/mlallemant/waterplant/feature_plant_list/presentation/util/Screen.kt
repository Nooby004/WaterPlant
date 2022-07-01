package com.mlallemant.waterplant.feature_plant_list.presentation.util

sealed class Screen(val route: String) {
    object PlantsScreen : Screen("plants_screen")
}