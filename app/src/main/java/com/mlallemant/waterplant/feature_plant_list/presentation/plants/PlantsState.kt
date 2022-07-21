package com.mlallemant.waterplant.feature_plant_list.presentation.plants

import com.mlallemant.waterplant.feature_plant_list.domain.model.Plant

data class PlantsState(
    val plants: List<Plant> = emptyList(),
    val currentPlant: Plant? = null
)