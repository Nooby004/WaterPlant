package com.mlallemant.waterplant.feature_plant_list.domain.use_case

import com.mlallemant.waterplant.feature_plant_list.domain.model.WaterPlant
import com.mlallemant.waterplant.feature_plant_list.domain.repository.PlantRepository

class GetWaterPlantsUseCase(
    private val repository: PlantRepository
) {
    suspend operator fun invoke(id: Int): List<WaterPlant> {
        return repository.getPlantWithWaterPlants(id).waterPlants.sortedByDescending { it.timestamp }
    }
}