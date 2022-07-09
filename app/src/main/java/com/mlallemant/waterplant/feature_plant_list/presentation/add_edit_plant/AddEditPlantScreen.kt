package com.mlallemant.waterplant.feature_plant_list.presentation.add_edit_plant

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    val showDialog = remember { mutableStateOf(false) }

    val newPicturePathState = navController.currentBackStackEntry?.savedStateHandle?.getStateFlow(
        "picturePath",
        ""
    )?.collectAsState()?.value

    val picturePathState =
        if (!newPicturePathState.isNullOrEmpty()) newPicturePathState else viewModel.picturePath.value

    val canDeletePlantState = viewModel.canDeletePlant.collectAsState().value

    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditPlantViewModel.UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditPlantViewModel.UiEvent.SavePlant -> {
                    navController.navigateUp()
                }
                is AddEditPlantViewModel.UiEvent.DeletePlant -> {
                    showDialog.value = false
                    navController.navigateUp()
                }
            }
        }
    }

    if (showDialog.value) {
        AlertDialog(title = {
            Text("Supprimer plante", style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(50.dp))
        }, text = {
            Text(
                "Voulez-vous vraiment supprimer cette plante ?",
                style = MaterialTheme.typography.body1
            )

        }, onDismissRequest = {
            showDialog.value = false
        },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.onEvent(AddEditPlantEvent.DeletePlant)
                }) {
                    Text(
                        "Oui",
                        color = MaterialTheme.colors.background,
                        style = MaterialTheme.typography.button
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text(
                        "Non",
                        color = MaterialTheme.colors.background,
                        style = MaterialTheme.typography.button
                    )
                }
            },
            backgroundColor = MaterialTheme.colors.primaryVariant
        )
    }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditPlantEvent.SavePlant(picturePathState))
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
                textStyle = MaterialTheme.typography.h6,
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
                textStyle = MaterialTheme.typography.h6,
                singleLine = true
            ) {
                viewModel.onEvent(AddEditPlantEvent.ChangeWaterFrequencyFocus(it))
            }

            Spacer(modifier = Modifier.height(32.dp))

            PlantImageView(
                picturePath = picturePathState
            ) {
                navController.navigate(Screen.TakePhotoScreen.route + "?plantId=${viewModel.getPlantId()}")
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (canDeletePlantState) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(onClick = {
                        showDialog.value = true
                    }) {
                        Text(
                            text = "Supprimer la plante",
                            color = Color.Red,
                            style = MaterialTheme.typography.h6
                        )
                    }
                }
            }
        }
    }
}