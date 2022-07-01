package com.mlallemant.waterplant.feature_plant_list.presentation.plants

import com.mlallemant.waterplant.feature_plant_list.domain.model.PlantWithWaterPlants

data class PlantsState(
    val plants: List<PlantWithWaterPlants> = emptyList()
)