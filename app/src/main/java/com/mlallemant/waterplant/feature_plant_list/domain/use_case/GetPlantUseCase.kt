package com.mlallemant.waterplant.feature_plant_list.domain.use_case

import com.mlallemant.waterplant.feature_plant_list.domain.model.Plant
import com.mlallemant.waterplant.feature_plant_list.domain.repository.PlantRepository

class GetPlantUseCase(
    private val repository: PlantRepository
) {
    suspend operator fun invoke(plantId: String): Plant {

        val plant = repository.getPlant(plantId)
        if (plant == null) {
            throw Exception("Plant is null")
        } else {
            return plant.copy(
                waterPlants = plant.waterPlants.toList()
                    .sortedByDescending { (_, v) -> v.timestamp }.toMap()
            )
        }
    }
}