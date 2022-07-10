package com.mlallemant.waterplant.feature_plant_list.domain.use_case

import com.mlallemant.waterplant.feature_plant_list.domain.repository.PlantRepository
import java.util.*
import java.util.concurrent.TimeUnit

class GetNextWateringUseCase(
    private val repository: PlantRepository
) {
    suspend operator fun invoke(id: Int): Long {
        val plantWithWaterPlants = repository.getPlantWithWaterPlants(id)



        plantWithWaterPlants?.let {
            val frequencyWatering = plantWithWaterPlants.plant.waterFrequency

            return if (it.waterPlants.isEmpty()) {
                // if no water plant, you should watering your plant now !
                -1
            } else {
                val lastWaterPlant = it.waterPlants.maxByOrNull { it1 -> it1.timestamp }?.timestamp

                val nextDay =
                    lastWaterPlant?.plus((1000 * 60 * 60 * 24 * frequencyWatering.toLong()))

                TimeUnit.MILLISECONDS.toDays(
                    nextDay?.minus(Calendar.getInstance().timeInMillis) ?: -1
                )

            }
        }

        return -1
    }


}
