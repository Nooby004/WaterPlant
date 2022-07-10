package com.mlallemant.waterplant.feature_plant_list.domain.use_case

import com.mlallemant.waterplant.feature_plant_list.domain.model.InvalidPlantException
import com.mlallemant.waterplant.feature_plant_list.domain.model.WaterPlant
import com.mlallemant.waterplant.feature_plant_list.domain.repository.PlantRepository

class AddWaterToPlantUseCase(
    private val repository: PlantRepository
) {

    @Throws(InvalidPlantException::class)
    suspend operator fun invoke(waterPlant: WaterPlant) {
        if (waterPlant.plantId == -1) {
            throw InvalidPlantException("The id of the plant can't be null.")
        }
        repository.addWaterPlant(waterPlant)
    }
}