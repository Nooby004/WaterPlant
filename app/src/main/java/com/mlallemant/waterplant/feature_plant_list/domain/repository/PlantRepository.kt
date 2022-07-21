package com.mlallemant.waterplant.feature_plant_list.domain.repository

import com.mlallemant.waterplant.feature_plant_list.domain.model.Plant
import com.mlallemant.waterplant.feature_plant_list.domain.model.WaterPlant
import kotlinx.coroutines.flow.Flow

interface PlantRepository {

    fun getPlants(): Flow<List<Plant>>

    suspend fun getPlant(plantId: String): Flow<Plant>

    suspend fun addPlant(plant: Plant)

    suspend fun addWaterPlant(plantId: String, waterPlant: WaterPlant)

    suspend fun deletePlant(plantId: String)
}