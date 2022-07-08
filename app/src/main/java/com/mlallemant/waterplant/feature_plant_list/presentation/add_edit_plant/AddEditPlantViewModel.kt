package com.mlallemant.waterplant.feature_plant_list.presentation.add_edit_plant

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlallemant.waterplant.feature_plant_list.domain.model.InvalidPlantException
import com.mlallemant.waterplant.feature_plant_list.domain.model.Plant
import com.mlallemant.waterplant.feature_plant_list.domain.use_case.PlantUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditPlantViewModel @Inject constructor(
    private val plantUseCases: PlantUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _plantName = mutableStateOf(
        PlantTextFieldState(hint = "Enter name...")
    )
    val plantName: State<PlantTextFieldState> = _plantName

    private val _waterFrequency = mutableStateOf(
        PlantTextFieldState(hint = "Enter water frequency...")
    )
    val waterFrequency: State<PlantTextFieldState> = _waterFrequency

    private val _picturePath = mutableStateOf(String())
    val picturePath: State<String> = _picturePath

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentPlantId: Int? = null

    init {
        savedStateHandle.get<Int>("plantId")?.let { plantId ->
            if (plantId != -1) {
                viewModelScope.launch {
                    plantUseCases.getPlantWithWaterPlants(plantId)?.also { plant ->
                        currentPlantId = plant.plant.id
                        _plantName.value = plantName.value.copy(
                            text = plant.plant.name,
                            isHintVisible = false
                        )
                        _waterFrequency.value = waterFrequency.value.copy(
                            text = plant.plant.waterFrequency,
                            isHintVisible = false
                        )
                    }

                }
            }
        }
    }

    fun getPlantId(): Int? {
        return currentPlantId
    }

    fun onEvent(event: AddEditPlantEvent) {
        when (event) {

            is AddEditPlantEvent.EnteredName -> {
                _plantName.value = plantName.value.copy(
                    text = event.value
                )
            }
            is AddEditPlantEvent.ChangeNameFocus -> {
                _plantName.value = plantName.value.copy(
                    isHintVisible = !event.focusState.isFocused && plantName.value.text.isBlank()
                )
            }
            is AddEditPlantEvent.EnteredWaterFrequency -> {
                _waterFrequency.value = waterFrequency.value.copy(
                    text = event.value
                )
            }
            is AddEditPlantEvent.ChangeWaterFrequencyFocus -> {
                _waterFrequency.value = waterFrequency.value.copy(
                    isHintVisible = !event.focusState.isFocused && waterFrequency.value.text.isBlank()
                )
            }
            is AddEditPlantEvent.SavePlant -> {
                viewModelScope.launch {
                    try {
                        plantUseCases.addPlant(
                            Plant(
                                name = plantName.value.text,
                                waterFrequency = waterFrequency.value.text,
                                picturePath = picturePath.value,
                                id = currentPlantId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidPlantException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: "Could not save plant !"
                            )
                        )
                    }
                }
            }
            is AddEditPlantEvent.OpenPreview -> {
            }
        }
    }


    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }
}