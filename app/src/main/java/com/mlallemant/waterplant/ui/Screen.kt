package com.mlallemant.waterplant.ui

sealed class Screen(val route: String) {
    object PlantsScreen : Screen("plants_screen")
    object AddEditPlantScreen : Screen("add_edit_plant_screen")
    object TakePhotoScreen : Screen("take_photo_screen")
    object AuthScreen : Screen("auth_screen")
    object SplashScreen : Screen("splash_screen")
    object SignUpScreen : Screen("sign_up_screen")
}