package com.mlallemant.waterplant.feature_plant_list.domain.use_case

import com.mlallemant.waterplant.feature_plant_list.domain.model.Plant
import com.mlallemant.waterplant.feature_plant_list.domain.repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPlantsUseCase(
    private val repository: PlantRepository
) {
    operator fun invoke(): Flow<List<Plant>> {
        return repository.getPlants().map { plantWithWaterPlants ->
            plantWithWaterPlants.sortedBy { it.name }
        }
    }

}