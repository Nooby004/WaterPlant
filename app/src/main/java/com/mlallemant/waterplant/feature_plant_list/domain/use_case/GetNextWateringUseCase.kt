package com.mlallemant.waterplant.feature_plant_list.domain.use_case

import com.mlallemant.waterplant.feature_plant_list.domain.repository.PlantRepository
import com.mlallemant.waterplant.feature_plant_list.domain.util.WateringUtils
import kotlinx.coroutines.flow.single

class GetNextWateringUseCase(
    private val repository: PlantRepository
) {
    suspend operator fun invoke(plantId: String): Long {
        return WateringUtils.getNextWateringDay(
            repository.getPlant(plantId).single()
        )
    }

}
