package com.mlallemant.waterplant.feature_plant_list.domain.use_case

import com.mlallemant.waterplant.feature_plant_list.domain.repository.PlantRepository
import com.mlallemant.waterplant.feature_plant_list.domain.util.WateringUtils

class GetNextWateringUseCase(
    private val repository: PlantRepository
) {
    suspend operator fun invoke(id: Int): Long {
        return WateringUtils.getNextWateringDay(
            repository.getPlantWithWaterPlants(id)
        )
    }
    
}
