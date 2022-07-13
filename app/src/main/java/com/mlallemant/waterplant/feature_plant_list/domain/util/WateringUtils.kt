package com.mlallemant.waterplant.feature_plant_list.domain.util

import com.mlallemant.waterplant.feature_plant_list.domain.model.PlantWithWaterPlants
import java.util.*
import java.util.concurrent.TimeUnit

class WateringUtils {

    companion object {

        fun getNextWateringDay(plantWithWaterPlants: PlantWithWaterPlants): Long {
            plantWithWaterPlants.let {
                val frequencyWatering = it.plant.waterFrequency

                return if (it.waterPlants.isEmpty()) {
                    // if no water plant, you should watering your plant now !
                    -1
                } else {
                    val lastWaterPlant =
                        it.waterPlants.maxByOrNull { it1 -> it1.timestamp }?.timestamp

                    val nextDay =
                        lastWaterPlant?.plus((1000 * 60 * 60 * 24 * frequencyWatering.toLong()))

                    TimeUnit.MILLISECONDS.toDays(
                        nextDay?.minus(Calendar.getInstance().timeInMillis) ?: -1
                    )

                }
            }
        }

    }
}