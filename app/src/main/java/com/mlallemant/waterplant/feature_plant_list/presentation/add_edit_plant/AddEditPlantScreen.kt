package com.mlallemant.waterplant.feature_plant_list.presentation.add_edit_plant

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mlallemant.waterplant.feature_plant_list.presentation.add_edit_plant.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditPlantScreen(
    navController: NavController,
    viewModel: AddEditPlantViewModel = hiltViewModel()
) {

    val nameState = viewModel.plantName.value

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
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save note")
            }
        },
        scaffoldState = scaffoldState
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            TransparentHintTextField(
                text = nameState.text,
                hint = nameState.hint,
                isHintVisible = nameState.isHintVisible,
                onValueChange = {
                    viewModel.onEvent(AddEditPlantEvent.EnteredName(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditPlantEvent.ChangeNameFocus(it))
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.h4
            )
            Spacer(modifier = Modifier.height(16.dp))

        }


    }
}