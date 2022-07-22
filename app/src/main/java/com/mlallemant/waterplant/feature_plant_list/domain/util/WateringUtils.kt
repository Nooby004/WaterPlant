package com.mlallemant.waterplant.feature_plant_list.domain.util

import com.mlallemant.waterplant.feature_plant_list.domain.model.Plant
import java.util.*
import java.util.concurrent.TimeUnit

class WateringUtils {

    companion object {

        fun getNextWateringDay(plant: Plant): Long {
            plant.let {
                val frequencyWatering = it.waterFrequency
                val waterPlants = it.waterPlants.values

                return if (waterPlants.isEmpty()) {
                    // if no water plant, you should watering your plant now !
                    -1
                } else {

                    // Get last watering timestamp
                    val lastWaterPlantTimestamp =
                        waterPlants.maxByOrNull { it1 -> it1.timestamp }?.timestamp

                    // Calculate the future watering day timestamp
                    val nextWateringDayTimestamp =
                        lastWaterPlantTimestamp?.plus((1000 * 60 * 60 * 24 * frequencyWatering.toLong()))

                    // Calculate the number of day before the next watering
                    val numberOfDayBeforeNextWatering =
                        nextWateringDayTimestamp?.minus(Calendar.getInstance().timeInMillis) ?: 0
                    TimeUnit.MILLISECONDS.toDays(
                        if (numberOfDayBeforeNextWatering <= 0) 0 else numberOfDayBeforeNextWatering
                    )
                }
            }
        }

    }
}