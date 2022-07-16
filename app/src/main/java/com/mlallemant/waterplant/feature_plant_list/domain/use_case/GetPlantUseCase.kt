package com.mlallemant.waterplant.feature_plant_list.domain.use_case

import com.mlallemant.waterplant.feature_plant_list.domain.model.PlantWithWaterPlants
import com.mlallemant.waterplant.feature_plant_list.domain.repository.PlantRepository

class GetPlantUseCase(
    private val repository: PlantRepository
) {
    suspend operator fun invoke(id: Int): PlantWithWaterPlants {
        return repository.getPlantWithWaterPlants(id)
    }
}