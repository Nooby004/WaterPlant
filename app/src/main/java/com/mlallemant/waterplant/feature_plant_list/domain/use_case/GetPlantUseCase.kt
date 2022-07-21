package com.mlallemant.waterplant.feature_plant_list.domain.use_case

import com.mlallemant.waterplant.feature_plant_list.domain.model.Plant
import com.mlallemant.waterplant.feature_plant_list.domain.repository.PlantRepository
import kotlinx.coroutines.flow.single

class GetPlantUseCase(
    private val repository: PlantRepository
) {
    suspend operator fun invoke(plantId: String): Plant {
        val plantWithWaterPlant = repository.getPlant(plantId)
        return plantWithWaterPlant.single().copy(
            waterPlants = plantWithWaterPlant.single().waterPlants.sortedByDescending { it.timestamp }
        )
    }
}