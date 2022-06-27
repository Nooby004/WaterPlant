package com.mlallemant.waterplant.feature_plant_list.domain.use_case

import com.mlallemant.waterplant.feature_plant_list.domain.model.InvalidPlantException
import com.mlallemant.waterplant.feature_plant_list.domain.model.Plant
import com.mlallemant.waterplant.feature_plant_list.domain.model.WaterPlant
import com.mlallemant.waterplant.feature_plant_list.domain.repository.PlantRepository

class AddWaterToPlantUseCase(
    private val repository: PlantRepository
) {

    @Throws(InvalidPlantException::class)
    suspend operator fun invoke(plant: Plant, waterPlant: WaterPlant) {
        if (plant.id == null) {
            throw InvalidPlantException("The id of the plant can't be null.")
        }
        repository.addWaterPlantToPlant(plant, waterPlant)
    }
}