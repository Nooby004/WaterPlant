package com.mlallemant.waterplant.feature_plant_list.presentation.plants

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlallemant.waterplant.feature_authentication.domain.use_case.AuthUseCases
import com.mlallemant.waterplant.feature_plant_list.domain.model.WaterPlant
import com.mlallemant.waterplant.feature_plant_list.domain.use_case.PlantUseCases
import com.mlallemant.waterplant.feature_plant_list.domain.util.WateringUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PlantsViewModel @Inject constructor(
    private val plantUseCases: PlantUseCases,
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _state = mutableStateOf(PlantsState())
    val state: State<PlantsState> = _state

    private val _nextWateringState: MutableStateFlow<Long> = MutableStateFlow(-1)
    val nextWateringState = _nextWateringState.asStateFlow()

    private val _picturePathState: MutableStateFlow<String> = MutableStateFlow("")
    val picturePathState = _picturePathState.asStateFlow()

    private var getPlantsJob: Job? = null

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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
                            ),
                            plantId = event.plantId
                        )

                        // Select current Plant
                        _state.value.plants.first { plant ->
                            plant.id == event.plantId
                        }.let {
                            _state.value = state.value.copy(
                                currentPlant = it
                            )
                            _nextWateringState.value = WateringUtils.getNextWateringDay(it)
                        }

                    } catch (e: Exception) {
                        Timber.e(e.toString())
                    }
                }
                // Actualize plants order
                //getPlants()
            }
            is PlantsEvent.SelectPlant -> {
                viewModelScope.launch {

                    // Select current Plant
                    _state.value.plants.first { plant ->
                        plant.id == event.plantId
                    }.let {
                        _state.value = state.value.copy(
                            currentPlant = it
                        )
                        _nextWateringState.value = WateringUtils.getNextWateringDay(it)
                    }
                }

            }
            is PlantsEvent.ShowImage -> {
                _picturePathState.value = event.picturePath
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authUseCases.signOut(onSuccess = {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.Logout)
                }
            }, onFailure = {
                /* TODO */
            })

        }
    }

    sealed class UiEvent {
        object Logout : UiEvent()
    }

}