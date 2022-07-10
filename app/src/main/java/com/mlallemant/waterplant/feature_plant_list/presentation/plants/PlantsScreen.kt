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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mlallemant.waterplant.R
import com.mlallemant.waterplant.feature_plant_list.presentation.core.navcontroller.extension.GetOnceResult
import com.mlallemant.waterplant.feature_plant_list.presentation.plants.components.AddItem
import com.mlallemant.waterplant.feature_plant_list.presentation.plants.components.PlantItem
import com.mlallemant.waterplant.feature_plant_list.presentation.plants.components.WaterPlantGrid
import com.mlallemant.waterplant.feature_plant_list.presentation.util.Screen
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlantsScreen(
    navController: NavController,
    viewModel: PlantsViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val waterPlantsState = viewModel.waterPlantsState.value

    val nextWateringDay = viewModel.nextWateringState.collectAsState().value

    val scope = rememberCoroutineScope()

    val lastPlantIdClicked = rememberSaveable { mutableStateOf(-1) }

    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    navController.GetOnceResult<String>("picturePath") {
        if (it.isNotEmpty()) {
            viewModel.onEvent(
                PlantsEvent.AddWaterToPlant(
                    lastPlantIdClicked.value,
                    it
                )
            )
        }
    }

    fun collapseSheet() {
        scope.launch {
            sheetState.collapse()
        }
    }


    fun selectPlant(id: Int) {
        lastPlantIdClicked.value = id
        viewModel.onEvent(PlantsEvent.SelectPlant(id))
        collapseSheet()
    }


    BottomSheetScaffold(

        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.TakePhotoScreen.route + "?plantId=${lastPlantIdClicked.value}")
                    collapseSheet()
                },
                backgroundColor = MaterialTheme.colors.primary,
                modifier = Modifier.run {
                    if (state.plants.isEmpty()) {
                        size(0.dp)
                    } else this
                }
            ) {
                Box(modifier = Modifier) {

                    Icon(
                        Icons.Default.WaterDrop,
                        contentDescription = "Water plant",
                        tint = MaterialTheme.colors.background,
                        modifier = Modifier.scale(1f)
                    )
                }
            }

        },
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
                                collapseSheet()
                                navController.navigate(Screen.AddEditPlantScreen.route + "?plantId=${lastPlantIdClicked.value}")
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
                .padding(it)
        ) {

            Text(
                modifier = Modifier.padding(12.dp),
                text = "Vos plantes",
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(modifier = Modifier.fillMaxWidth()) {

                item {
                    Spacer(modifier = Modifier.width(8.dp))
                    AddItem(
                        onAdd = {
                            navController.navigate(Screen.AddEditPlantScreen.route)
                        },
                        modifier = Modifier
                            .width(80.dp)
                            .height(100.dp)
                    )
                }

                items(state.plants) { plant ->
                    if (lastPlantIdClicked.value == -1) {
                        plant.id?.let { id ->
                            selectPlant(id)
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    PlantItem(
                        plant = plant,
                        onClick = {
                            plant.id?.let { id ->
                                selectPlant(id)
                            }
                        },
                        isSelected = lastPlantIdClicked.value == plant.id,
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
                        Spacer(modifier = Modifier.width(12.dp))
                    }

                }
            }

            if (nextWateringDay != -1L) {
                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp, 0.dp, 0.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    GlideImage(
                        imageModel = R.mipmap.watering_can,
                        contentScale = ContentScale.Inside,
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary),
                        modifier = Modifier
                            .size(40.dp)
                    )

                    Text(
                        text = "Prochain arrosage dans $nextWateringDay jours",
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.body2
                    )
                }

            }

            if (lastPlantIdClicked.value != -1) {
                Spacer(modifier = Modifier.height(16.dp))
                WaterPlantGrid(waterPlants = waterPlantsState.waterPlants)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 0.dp, 0.dp, 50.dp)
                .zIndex(10f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            OutlinedButton(
                onClick = {
                    navController.navigate(Screen.TakePhotoScreen.route + "?plantId=${lastPlantIdClicked.value}")
                },
                modifier = Modifier
                    .size(80.dp),
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),  //avoid the little icon
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colors.secondary,
                    backgroundColor = MaterialTheme.colors.secondary
                )
            ) {

                Box(modifier = Modifier) {

                    Icon(
                        Icons.Default.WaterDrop,
                        contentDescription = "Water plant",
                        tint = MaterialTheme.colors.background,
                        modifier = Modifier.scale(2f)
                    )

                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Water plant",
                        tint = MaterialTheme.colors.secondary,
                        modifier = Modifier.scale(0.8f)
                    )

                }
            }

        }
    }
}



