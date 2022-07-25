package com.mlallemant.waterplant.feature_plant_list.presentation.plants


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mlallemant.waterplant.R
import com.mlallemant.waterplant.feature_plant_list.domain.model.WaterPlant
import com.mlallemant.waterplant.feature_plant_list.presentation.core.navcontroller.extension.GetOnceResult
import com.mlallemant.waterplant.feature_plant_list.presentation.plants.components.ImageViewer
import com.mlallemant.waterplant.feature_plant_list.presentation.plants.components.StickyPlantsHeader
import com.mlallemant.waterplant.feature_plant_list.presentation.plants.components.WaterPlantGrid
import com.mlallemant.waterplant.ui.Screen
import kotlinx.coroutines.flow.collectLatest
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

    val lastPlantIdClicked = rememberSaveable { mutableStateOf("-1") }

    val showImage = remember { mutableStateOf(false) }

    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = MaterialTheme.colors.primaryVariant)
    systemUiController.setNavigationBarColor(
        color = MaterialTheme.colors.onBackground,
        darkIcons = true,
        navigationBarContrastEnforced = true
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


    fun selectPlant(id: String) {
        lastPlantIdClicked.value = id
        viewModel.onEvent(PlantsEvent.SelectPlant(id))
        collapseSheet()
    }

    fun editPlant(plantId: String) {
        scope.launch {
            if (sheetState.isCollapsed) {
                sheetState.expand()
            } else {
                sheetState.collapse()
            }
        }
        lastPlantIdClicked.value = plantId
    }


    fun addNewPlant() {
        navController.navigate(Screen.AddEditPlantScreen.route)
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is PlantsViewModel.UiEvent.Logout -> {
                    navController.navigate(Screen.AuthScreen.route)
                }
            }
        }
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
                        painter = painterResource(id = R.mipmap.watering_can),
                        contentDescription = "Water plant",
                        tint = MaterialTheme.colors.background,
                        modifier = Modifier
                            .size(24.dp)
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
                        .height(50.dp)
                        .clip(shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .background(MaterialTheme.colors.primaryVariant),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "See information about the plant",
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
        sheetBackgroundColor = Color.Transparent,
        sheetPeekHeight = 0.dp
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primaryVariant)
                .padding(it)

        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 16.dp)
            ) {

                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    text = "Water Plant",
                    color = MaterialTheme.colors.background,
                    style = MaterialTheme.typography.h6
                )

                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Sign out",
                    tint = MaterialTheme.colors.background,
                    modifier = Modifier
                        .clickable {
                            viewModel.logout()
                        }
                        .align(Alignment.BottomEnd)
                        .padding(12.dp, 0.dp)
                )

            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(30.dp, 30.dp))
                    .background(color = MaterialTheme.colors.onBackground)
            )
            {

                var waterPlants = emptyList<WaterPlant>()
                state.currentPlant?.waterPlants?.values?.let { waterPlantList ->
                    waterPlants =
                        waterPlantList.toList()
                }

                WaterPlantGrid(
                    stickyHeader = {
                        StickyPlantsHeader(
                            onAddNewPlantClick = { addNewPlant() },
                            plants = state.plants,
                            lastPlantIdClicked = lastPlantIdClicked.value,
                            onEditPlant = { plantId -> editPlant(plantId) },
                            onSelectPlant = { plantId -> selectPlant(plantId) },
                            nextWatering = state.nextWateringDay,
                            currentPlant = state.currentPlant
                        )
                    },
                    waterPlants = waterPlants
                ) { picturePath ->
                    viewModel.onEvent(PlantsEvent.ShowImage(picturePath))
                    showImage.value = true
                }

            }

        }


        ImageViewer(showDialog = showImage.value, onClose = {
            showImage.value = false
        }, state.picturePath)

    }
}




