package com.mlallemant.waterplant.feature_plant_list.presentation.plants

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlallemant.waterplant.feature_plant_list.domain.use_case.PlantUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

}