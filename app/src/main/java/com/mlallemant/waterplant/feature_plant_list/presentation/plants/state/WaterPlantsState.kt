package com.mlallemant.waterplant.feature_plant_list.presentation.plants.state

import com.mlallemant.waterplant.feature_plant_list.domain.model.WaterPlant


data class WaterPlantsState(
    val waterPlants: List<WaterPlant> = emptyList()
)
