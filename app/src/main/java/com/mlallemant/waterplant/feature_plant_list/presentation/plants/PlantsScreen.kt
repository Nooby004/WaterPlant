package com.mlallemant.waterplant.feature_plant_list.presentation.plants


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mlallemant.waterplant.R
import com.mlallemant.waterplant.feature_plant_list.presentation.core.navcontroller.extension.GetOnceResult
import com.mlallemant.waterplant.feature_plant_list.presentation.plants.components.ImageViewer
import com.mlallemant.waterplant.feature_plant_list.presentation.plants.components.PlantItem
import com.mlallemant.waterplant.feature_plant_list.presentation.plants.components.WaterPlantGrid
import com.mlallemant.waterplant.ui.Screen
import com.skydoves.landscapist.glide.GlideImage
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
    systemUiController.setNavigationBarColor(color = MaterialTheme.colors.onBackground)


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
                        .height(60.dp)
                        .clip(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .background(MaterialTheme.colors.primary),
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
                        .align(Alignment.BottomCenter),
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

                Spacer(modifier = Modifier.height(8.dp))


                Button(
                    onClick = {
                        navController.navigate(Screen.AddEditPlantScreen.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(0.1f)
                        .padding(12.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.primaryVariant
                    )
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "content description",
                        tint = MaterialTheme.colors.primaryVariant
                    )
                    Text(text = "Add new plant", style = MaterialTheme.typography.subtitle2)
                }

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 12.dp, 0.dp, 0.dp)
                ) {

                    items(state.plants) { plant ->
                        if (lastPlantIdClicked.value == "-1") {
                            selectPlant(plant.id)
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        PlantItem(
                            plant = plant,
                            onClick = {
                                selectPlant(plant.id)
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
                                lastPlantIdClicked.value = plantId
                            },
                            modifier = Modifier
                                .width(100.dp)
                                .height(140.dp)

                        )

                        if (plant.id == state.plants.last().id) {
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                    }
                }


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp, 0.dp)
                ) {


                    if (state.nextWateringDay != -1L) {

                        Column(modifier = Modifier.fillMaxWidth()) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 6.dp),
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
                                    text = when (state.nextWateringDay) {
                                        0L -> "Veuillez arroser la plante aujourd'hui !"
                                        1L -> "Prochain arrosage dans ${state.nextWateringDay}) jour"
                                        else -> "Prochain arrosage dans ${state.nextWateringDay} jours"
                                    },
                                    modifier = Modifier
                                        .padding(8.dp),
                                    style = MaterialTheme.typography.body1,
                                    color = when (state.nextWateringDay) {
                                        0L -> MaterialTheme.colors.secondary
                                        else -> MaterialTheme.colors.background
                                    }
                                )
                            }

                            state.currentPlant?.name?.let { it ->

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = "Watering of the plant",
                                        style = MaterialTheme.typography.h6,
                                        color = MaterialTheme.colors.background
                                    )

                                    Text(
                                        text = " $it",
                                        style = MaterialTheme.typography.h6,
                                        color = MaterialTheme.colors.primaryVariant
                                    )
                                }


                            }

                        }


                    }

                    if (lastPlantIdClicked.value != "-1") {
                        Spacer(modifier = Modifier.height(8.dp))

                        state.currentPlant?.waterPlants?.values?.let { it1 ->
                            WaterPlantGrid(
                                waterPlants = it1.toList()
                            ) { picturePath ->
                                viewModel.onEvent(PlantsEvent.ShowImage(picturePath))
                                showImage.value = true
                            }
                        }
                    }
                }

            }


        }


        ImageViewer(showDialog = showImage.value, onClose = {
            showImage.value = false
        }, state.picturePath)

    }
}



