package com.mlallemant.waterplant.feature_plant_list.domain.use_case

import com.mlallemant.waterplant.feature_plant_list.domain.model.PlantWithWaterPlants
import com.mlallemant.waterplant.feature_plant_list.domain.repository.PlantRepository

class GetPlantUseCase(
    private val repository: PlantRepository
) {
    suspend operator fun invoke(id: Int): PlantWithWaterPlants {
        val plantWithWaterPlant = repository.getPlantWithWaterPlants(id)
        return plantWithWaterPlant.copy(
            waterPlants = plantWithWaterPlant.waterPlants.sortedByDescending { it.timestamp }
        )
    }
}