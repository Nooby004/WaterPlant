package com.mlallemant.waterplant.feature_plant_list.presentation.plants

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlallemant.waterplant.feature_plant_list.domain.model.InvalidPlantException
import com.mlallemant.waterplant.feature_plant_list.domain.model.WaterPlant
import com.mlallemant.waterplant.feature_plant_list.domain.use_case.PlantUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _nextWateringState: MutableStateFlow<Long> = MutableStateFlow(-1)
    val nextWateringState = _nextWateringState.asStateFlow()

    private val _picturePathState: MutableStateFlow<String> = MutableStateFlow("")
    val picturePathState = _picturePathState.asStateFlow()

    private var getPlantsJob: Job? = null

    init {
        getPlants()
    }

    private fun getPlants() {
        getPlantsJob?.cancel()
        getPlantsJob = plantUseCases.getPlants()
            .onEach { plants ->
                _state.value = state.value.copy(
                    plants = plants,
                )
            }.launchIn(viewModelScope)
    }

    private fun getPlant(plantId: Int) {
        viewModelScope.launch {
            plantUseCases.getPlant(
                plantId
            ).let {
                _state.value = state.value.copy(
                    currentPlant = it
                )
            }
        }
    }

    fun onEvent(event: PlantsEvent) {
        when (event) {
            is PlantsEvent.AddWaterToPlant -> {

                viewModelScope.launch {
                    try {

                        // Add water to plant
                        plantUseCases.addWaterToPlant(
                            waterPlant = WaterPlant(
                                picturePath = event.picturePath,
                                timestamp = Calendar.getInstance().timeInMillis,
                                plantId = event.plantId
                            )
                        )

                        // Retrieve next watering
                        plantUseCases.getNextWatering(
                            event.plantId
                        ).let {
                            _nextWateringState.value = it
                        }

                        // Retrieve water plant list
                        plantUseCases.getPlant(
                            event.plantId
                        ).let {
                            _state.value = state.value.copy(
                                currentPlant = it
                            )
                        }

                    } catch (e: InvalidPlantException) {
                        Log.e("TAG", e.toString())
                    }
                }

                // Actualize plants order
                getPlants()
            }
            is PlantsEvent.SelectPlant -> {
                viewModelScope.launch {

                    // Retrieve water plant list
                    plantUseCases.getPlant(
                        event.plantId
                    ).let {
                        _state.value = state.value.copy(
                            currentPlant = it
                        )
                    }

                    // Retrieve next watering
                    plantUseCases.getNextWatering(
                        event.plantId
                    ).let {
                        _nextWateringState.value = it
                    }
                }

            }
            is PlantsEvent.ShowImage -> {
                _picturePathState.value = event.picturePath
            }
        }
    }

}