package com.mlallemant.waterplant.feature_plant_list.presentation.add_edit_plant

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlallemant.waterplant.feature_plant_list.domain.model.Plant
import com.mlallemant.waterplant.feature_plant_list.domain.use_case.PlantUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.util.*
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

    private val _canDeletePlant: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val canDeletePlant = _canDeletePlant.asStateFlow()

    private val _showDialog: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val showDialog = _showDialog.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentPlantId: String = "-1"

    init {
        savedStateHandle.get<String>("plantId")?.let { plantId ->
            if (plantId != "-1") {
                viewModelScope.launch {
                    plantUseCases.getPlant(plantId).also { plant ->
                        currentPlantId = plant.id
                        _plantName.value = plantName.value.copy(
                            text = plant.name,
                            isHintVisible = false
                        )
                        _waterFrequency.value = waterFrequency.value.copy(
                            text = plant.waterFrequency,
                            isHintVisible = false
                        )
                        _picturePath.value = plant.picturePath
                        _canDeletePlant.value = true
                    }

                }
            }
        }
    }

    fun getPlantId(): String {
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
                event.picturePath?.let {
                    it.isEmpty().not().let {

                        viewModelScope.launch {
                            if (event.picturePath != _picturePath.value && _picturePath.value.isNotEmpty()) {
                                // Delete file
                                File(_picturePath.value).delete()
                            }
                        }

                        _picturePath.value = event.picturePath
                    }
                }
                viewModelScope.launch {
                    try {
                        plantUseCases.addPlant(
                            Plant(
                                name = plantName.value.text,
                                waterFrequency = waterFrequency.value.text,
                                picturePath = picturePath.value,
                                id = if (currentPlantId == "-1") UUID.randomUUID()
                                    .toString() else currentPlantId
                            )
                        )
                        _eventFlow.emit(UiEvent.SavePlant)
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: "Could not save plant !"
                            )
                        )
                    }
                }
            }
            is AddEditPlantEvent.DeletePlant -> {
                viewModelScope.launch {
                    try {
                        plantUseCases.deletePlant(
                            Plant(
                                name = plantName.value.text,
                                waterFrequency = waterFrequency.value.text,
                                picturePath = picturePath.value,
                                id = currentPlantId
                            )
                        )
                        _showDialog.value = false
                        _eventFlow.emit(UiEvent.DeletePlant)
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(
                                message = e.message ?: "Could not save plant !"
                            )
                        )
                    }
                }
            }
            is AddEditPlantEvent.ShowDialog -> {
                _showDialog.value = event.show
            }
        }
    }


    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        object SavePlant : UiEvent()
        object DeletePlant : UiEvent()
    }
}