package com.mlallemant.waterplant.feature_plant_list.domain.use_case

import com.mlallemant.waterplant.feature_plant_list.domain.model.InvalidPlantException
import com.mlallemant.waterplant.feature_plant_list.domain.model.Plant
import com.mlallemant.waterplant.feature_plant_list.domain.repository.PlantRepository

class AddPlantUseCase(
    private val repository: PlantRepository
) {

    @Throws(InvalidPlantException::class)
    suspend operator fun invoke(plant: Plant) {
        if (plant.name.isBlank()) {
            throw InvalidPlantException("The name of the plant can't be empty.")
        }
        repository.addPlant(plant)
    }
}