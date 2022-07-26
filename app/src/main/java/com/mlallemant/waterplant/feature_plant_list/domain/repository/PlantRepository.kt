package com.mlallemant.waterplant.feature_plant_list.domain.repository

import com.mlallemant.waterplant.feature_plant_list.domain.model.Plant
import com.mlallemant.waterplant.feature_plant_list.domain.model.WaterPlant
import kotlinx.coroutines.flow.Flow

interface PlantRepository {

    fun getPlants(): Flow<List<Plant>>

    suspend fun getPlant(plantId: String): Plant?

    suspend fun savePlant(plant: Plant)

    suspend fun addWaterPlant(plantId: String, waterPlant: WaterPlant)

    suspend fun deletePlant(plantId: String)
}