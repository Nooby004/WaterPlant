package com.mlallemant.waterplant.feature_plant_list.domain.use_case

import com.mlallemant.waterplant.feature_plant_list.domain.model.PlantWithWaterPlants
import com.mlallemant.waterplant.feature_plant_list.domain.repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPlantsWithWaterPlantsUseCase(
    private val repository: PlantRepository
) {
    operator fun invoke(): Flow<List<PlantWithWaterPlants>> {
        return repository.getPlantsWithWaterPlants().map { plantWithWaterPlants ->
            plantWithWaterPlants.sortedBy { it.plant.name }
        }
    }

}