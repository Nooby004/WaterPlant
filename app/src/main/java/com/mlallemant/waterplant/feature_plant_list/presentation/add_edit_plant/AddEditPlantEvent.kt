package com.mlallemant.waterplant.feature_plant_list.presentation.add_edit_plant

import androidx.compose.ui.focus.FocusState

sealed class AddEditPlantEvent {
    data class EnteredName(val value: String) : AddEditPlantEvent()
    data class ChangeNameFocus(val focusState: FocusState) : AddEditPlantEvent()
    data class EnteredWaterFrequency(val value: String) : AddEditPlantEvent()
    data class ChangeWaterFrequencyFocus(val focusState: FocusState) : AddEditPlantEvent()
    data class SavePlant(val picturePath: String?) : AddEditPlantEvent()
    object DeletePlant : AddEditPlantEvent()

}
