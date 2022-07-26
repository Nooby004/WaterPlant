package com.mlallemant.waterplant.feature_plant_list.domain.use_case

import com.mlallemant.waterplant.feature_plant_list.domain.model.Plant
import com.mlallemant.waterplant.feature_plant_list.domain.repository.PlantRepository

class SavePlantUseCase(
    private val repository: PlantRepository
) {

    suspend operator fun invoke(plant: Plant) {
        if (plant.name.isBlank()) {
            throw Exception("The name of the plant can't be empty.")
        }
        if (plant.waterFrequency.isBlank()) {
            throw Exception("The water frequency of the plant can't be empty.")
        }
        if (plant.picturePath.isBlank()) {
            throw Exception("The plant must have a picture.")
        }
        repository.savePlant(plant)
    }
}