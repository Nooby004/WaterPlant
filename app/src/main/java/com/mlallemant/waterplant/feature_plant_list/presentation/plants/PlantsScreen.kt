package com.mlallemant.waterplant.feature_plant_list.presentation.plants


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditPlantScreen.route)
                },
                backgroundColor = MaterialTheme.colors.onBackground
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Plant")
            }
        },
        scaffoldState = scaffoldState
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp)
        ) {

            Text(
                text = "Your plants",
                style = MaterialTheme.typography.h4
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.plants) { plant ->

                    PlantItem(
                        plantWithWaterPlant = plant,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(Screen.AddEditPlantScreen.route + "?plantId=${plant.plant.id}")
                            },
                        onDeleteClick = {
                            /*TODO*/
                        })
                    
                    Spacer(modifier = Modifier.height(16.dp))

                }

            }
        }
    }
}

