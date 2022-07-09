package com.mlallemant.waterplant.feature_plant_list.presentation.plants


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mlallemant.waterplant.feature_plant_list.presentation.plants.components.AddItem
import com.mlallemant.waterplant.feature_plant_list.presentation.plants.components.PlantItem
import com.mlallemant.waterplant.feature_plant_list.presentation.util.Screen
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlantsScreen(
    navController: NavController,
    viewModel: PlantsViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val scope = rememberCoroutineScope()

    val lastPlantIdClicked = remember { mutableStateOf(-1) }

    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .background(MaterialTheme.colors.primaryVariant),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "Voir les informations de la plante",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.background,
                        modifier = Modifier
                            .clickable {
                                navController.navigate(Screen.AddEditPlantScreen.route + "?plantId=${lastPlantIdClicked.value}")
                                scope.launch {
                                    sheetState.collapse()
                                }
                            }
                            .padding(8.dp)
                    )
                }
            }

        },
        sheetBackgroundColor = MaterialTheme.colors.background,
        sheetPeekHeight = 0.dp
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            Arrangement.SpaceAround
        ) {

            Text(
                modifier = Modifier.padding(16.dp),
                text = "Vos plantes",
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(state.plants) { plant ->

                    if (plant == state.plants.first()) {
                        Spacer(modifier = Modifier.width(16.dp))

                        AddItem(
                            onAdd = {
                                navController.navigate(Screen.AddEditPlantScreen.route)
                            },
                            modifier = Modifier
                                .width(80.dp)
                                .height(100.dp)
                        )

                        if (lastPlantIdClicked.value == -1) {
                            lastPlantIdClicked.value = plant.plant.id!!
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    PlantItem(
                        plantWithWaterPlant = plant,
                        onClick = {
                            lastPlantIdClicked.value = plant.plant.id!!
                        },
                        isSelected = lastPlantIdClicked.value == plant.plant.id,
                        onOpenEdit = { plantId ->
                            scope.launch {
                                if (sheetState.isCollapsed) {
                                    sheetState.expand()
                                } else {
                                    sheetState.collapse()
                                }
                            }
                            if (plantId != null) {
                                lastPlantIdClicked.value = plantId
                            }
                        },
                        modifier = Modifier
                            .width(80.dp)
                            .height(100.dp)

                    )

                    if (plant == state.plants.last()) {
                        Spacer(modifier = Modifier.width(16.dp))
                    }

                }
            }

            if (lastPlantIdClicked.value != -1) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp, 0.dp, 0.dp, 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {

                    OutlinedButton(
                        onClick = { viewModel.onEvent(PlantsEvent.AddWaterToPlant(lastPlantIdClicked.value)) },
                        modifier = Modifier
                            .size(80.dp),
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp),  //avoid the little icon
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colors.background,
                            backgroundColor = MaterialTheme.colors.background
                        )
                    ) {

                        Box(modifier = Modifier) {

                            Icon(
                                Icons.Default.WaterDrop,
                                contentDescription = "Water plant",
                                tint = MaterialTheme.colors.secondary,
                                modifier = Modifier.scale(2f)
                            )

                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Water plant",
                                tint = MaterialTheme.colors.background,
                                modifier = Modifier.scale(0.8f)
                            )

                        }
                    }

                }
            }


        }
    }
}


