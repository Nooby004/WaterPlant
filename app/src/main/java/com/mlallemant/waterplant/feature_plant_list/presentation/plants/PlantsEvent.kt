package com.mlallemant.waterplant.feature_plant_list.presentation.plants


sealed class PlantsEvent {
    data class AddWaterToPlant(val plantId: String, val picturePath: String) : PlantsEvent()
    data class SelectPlant(val plantId: String) : PlantsEvent()
    data class ShowImage(val picturePath: String) : PlantsEvent()
}