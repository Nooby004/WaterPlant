package com.mlallemant.waterplant.feature_plant_list.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mlallemant.waterplant.feature_plant_list.presentation.add_edit_plant.AddEditPlantScreen
import com.mlallemant.waterplant.feature_plant_list.presentation.plants.PlantsScreen
import com.mlallemant.waterplant.feature_plant_list.presentation.util.Screen
import com.mlallemant.waterplant.ui.theme.WaterPlantTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaterPlantTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.PlantsScreen.route
                    ) {
                        composable(route = Screen.PlantsScreen.route) {
                            PlantsScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditPlantScreen.route + "?plantId={plantId}",
                            arguments = listOf(
                                navArgument(
                                    name = "plantId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                }

                            )
                        ) {
                            AddEditPlantScreen(navController = navController)
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WaterPlantTheme {
        Greeting("Android")
    }
}