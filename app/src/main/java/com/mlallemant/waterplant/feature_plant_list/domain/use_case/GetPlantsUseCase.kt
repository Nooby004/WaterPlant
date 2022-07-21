package com.mlallemant.waterplant.feature_plant_list.domain.use_case

import com.mlallemant.waterplant.feature_plant_list.domain.model.Plant
import com.mlallemant.waterplant.feature_plant_list.domain.repository.PlantRepository
import com.mlallemant.waterplant.feature_plant_list.domain.util.WateringUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single

class GetPlantsUseCase(
    private val repository: PlantRepository
) {
    operator fun invoke(): Flow<List<Plant>> {

        return repository.getPlants().map { plants ->

            // create map<id, wateringDay>
            val idWateringDayMap = mutableMapOf<String, Long>()

            // populate the map
            plants.forEach {
                val nextWateringDay =
                    WateringUtils.getNextWateringDay(repository.getPlant(it.id).single())
                idWateringDayMap[it.id] = nextWateringDay
            }

            // retrieve sorted plant id list
            val sortedId =
                idWateringDayMap.toList().sortedBy { (_, value) -> value }.toMap().keys.toList()

            // sort plant list depending of the sorted id list
            plants.sortedWith { plant1, plant2 ->
                sortedId.indexOf(plant1.id).compareTo(sortedId.indexOf(plant2.id))
            }
        }
    }

}