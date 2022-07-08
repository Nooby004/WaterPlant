package com.mlallemant.waterplant.feature_plant_list.presentation.add_edit_plant

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mlallemant.waterplant.feature_plant_list.presentation.add_edit_plant.components.PlantImageView
import com.mlallemant.waterplant.feature_plant_list.presentation.add_edit_plant.components.PlantTextField
import com.mlallemant.waterplant.feature_plant_list.presentation.util.Screen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditPlantScreen(
    navController: NavController,
    viewModel: AddEditPlantViewModel = hiltViewModel()
) {

    val nameState = viewModel.plantName.value
    val waterFrequencyState = viewModel.waterFrequency.value
    val focusManager = LocalFocusManager.current

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditPlantViewModel.UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditPlantViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditPlantEvent.SavePlant)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save plant",
                    tint = MaterialTheme.colors.background
                )
            }
        },
        scaffoldState = scaffoldState
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
        ) {

            PlantTextField(
                title = "Nom de la plante",
                text = nameState.text,
                onValueChange = {
                    viewModel.onEvent(AddEditPlantEvent.EnteredName(it))
                },
                textStyle = MaterialTheme.typography.h5,
                singleLine = true
            ) {
                viewModel.onEvent(AddEditPlantEvent.ChangeNameFocus(it))
            }
            Spacer(modifier = Modifier.height(16.dp))

            PlantTextField(
                title = "Fréquence d'arrosage (j)",
                text = waterFrequencyState.text,
                keyboardType = KeyboardType.Number,
                onValueChange = {
                    viewModel.onEvent(AddEditPlantEvent.EnteredWaterFrequency(it))
                },
                textStyle = MaterialTheme.typography.h5,
                singleLine = true
            ) {
                viewModel.onEvent(AddEditPlantEvent.ChangeWaterFrequencyFocus(it))
            }

            Spacer(modifier = Modifier.height(16.dp))

            PlantImageView(
                onClick = {
                    navController.navigate(Screen.TakePhotoScreen.route + "?plantId=${viewModel.getPlantId()}")
                }
            )

        }


    }
}