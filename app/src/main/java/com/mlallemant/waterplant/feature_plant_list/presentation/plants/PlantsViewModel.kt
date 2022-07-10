package com.mlallemant.waterplant.feature_plant_list.presentation.plants

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlallemant.waterplant.feature_plant_list.domain.model.InvalidPlantException
import com.mlallemant.waterplant.feature_plant_list.domain.model.WaterPlant
import com.mlallemant.waterplant.feature_plant_list.domain.use_case.PlantUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PlantsViewModel @Inject constructor(
    private val plantUseCases: PlantUseCases
) : ViewModel() {

    private val _state = mutableStateOf(PlantsState())
    val state: State<PlantsState> = _state

    private var getPlantsJob: Job? = null

    init {
        getPlants()
    }


    private fun getPlants() {
        getPlantsJob?.cancel()
        getPlantsJob = plantUseCases.getPlantsWithWaterPlants()
            .onEach { plantWithWaterPlants ->
                _state.value = state.value.copy(
                    plants = plantWithWaterPlants
                )
            }.launchIn(viewModelScope)
    }

    fun onEvent(event: PlantsEvent) {
        when (event) {
            is PlantsEvent.AddWaterToPlant -> {

                viewModelScope.launch {
                    try {
                        plantUseCases.addWaterToPlant(
                            waterPlant = WaterPlant(
                                picturePath = event.picturePath,
                                timestamp = Calendar.getInstance().timeInMillis,
                                plantId = event.plantId
                            )
                        )
                        //_eventFlow.emit(AddEditPlantViewModel.UiEvent.SavePlant)
                    } catch (e: InvalidPlantException) {
                        /* _eventFlow.emit(
                             AddEditPlantViewModel.UiEvent.ShowSnackBar(
                                 message = e.message ?: "Could not save plant !"
                             )
                         )*/
                    }
                }
            }
        }
    }

}