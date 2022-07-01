package com.mlallemant.waterplant.feature_plant_list.presentation.add_edit_plant

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mlallemant.waterplant.feature_plant_list.domain.use_case.PlantUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEditPlantViewModel @Inject constructor(
    private val plantUseCases: PlantUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
}