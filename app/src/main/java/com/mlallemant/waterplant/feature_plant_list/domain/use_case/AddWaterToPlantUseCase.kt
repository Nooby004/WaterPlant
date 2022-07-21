package com.mlallemant.waterplant.feature_plant_list.domain.use_case

import com.mlallemant.waterplant.feature_plant_list.domain.model.WaterPlant
import com.mlallemant.waterplant.feature_plant_list.domain.repository.PlantRepository

class AddWaterToPlantUseCase(
    private val repository: PlantRepository
) {

    suspend operator fun invoke(plantId: String, waterPlant: WaterPlant) {
        if (plantId == "-1") {
            throw Exception("The id of the plant can't be null.")
        }
        repository.addWaterPlant(plantId, waterPlant)
    }
}