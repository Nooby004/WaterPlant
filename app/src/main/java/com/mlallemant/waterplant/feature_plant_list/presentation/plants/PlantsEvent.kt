package com.mlallemant.waterplant.feature_plant_list.presentation.plants


sealed class PlantsEvent {
    object DeletePlant : PlantsEvent()
}