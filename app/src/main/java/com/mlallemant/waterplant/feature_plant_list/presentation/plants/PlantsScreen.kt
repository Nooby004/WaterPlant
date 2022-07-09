package com.mlallemant.waterplant.feature_plant_list.presentation.plants


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mlallemant.waterplant.feature_plant_list.presentation.plants.components.AddItem
import com.mlallemant.waterplant.feature_plant_list.presentation.plants.components.PlantItem
import com.mlallemant.waterplant.feature_plant_list.presentation.util.Screen

@Composable
fun PlantsScreen(
    navController: NavController,
    viewModel: PlantsViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            Arrangement.SpaceAround
        ) {

            Text(
                text = "Vos plantes",
                style = MaterialTheme.typography.h4
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(modifier = Modifier.fillMaxSize()) {
                items(state.plants) { plant ->

                    PlantItem(
                        plantWithWaterPlant = plant,
                        onEdit = {
                            navController.navigate(Screen.AddEditPlantScreen.route + "?plantId=${plant.plant.id}")
                        },
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(LocalConfiguration.current.screenWidthDp.dp - 32.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    if (plant == state.plants.last()) {
                        AddItem(
                            onAdd = {
                                navController.navigate(Screen.AddEditPlantScreen.route)
                            },
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(LocalConfiguration.current.screenWidthDp.dp - 32.dp)
                        )
                    }


                }
            }



            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier) {
                Text(
                    text = "Vos plantes",
                    style = MaterialTheme.typography.h4
                )
            }
        }
    }
}

