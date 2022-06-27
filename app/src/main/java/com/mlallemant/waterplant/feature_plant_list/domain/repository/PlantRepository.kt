package com.mlallemant.waterplant.feature_plant_list.domain.repository

import com.mlallemant.waterplant.feature_plant_list.domain.model.Plant
import com.mlallemant.waterplant.feature_plant_list.domain.model.PlantWithWaterPlants
import com.mlallemant.waterplant.feature_plant_list.domain.model.WaterPlant
import kotlinx.coroutines.flow.Flow

interface PlantRepository {

    fun getPlantsWithWaterPlants(): Flow<List<PlantWithWaterPlants>>

    suspend fun addPlant(plant: Plant)

    suspend fun addWaterPlantToPlant(plant: Plant, waterPlant: WaterPlant)

    suspend fun deletePlant(plant: Plant)
}