package com.mlallemant.waterplant.feature_plant_list.data.repository

import com.mlallemant.waterplant.feature_plant_list.data.data_source.PlantDao
import com.mlallemant.waterplant.feature_plant_list.domain.model.Plant
import com.mlallemant.waterplant.feature_plant_list.domain.model.PlantWithWaterPlants
import com.mlallemant.waterplant.feature_plant_list.domain.model.WaterPlant
import com.mlallemant.waterplant.feature_plant_list.domain.repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import java.io.File

class PlantRepositoryImpl(
    private val dao: PlantDao
) : PlantRepository {

    override fun getPlantsWithWaterPlants(): Flow<List<PlantWithWaterPlants>> {
        return dao.getPlantsWithWaterPlants()
    }

    override suspend fun getPlantWithWaterPlants(id: Int): PlantWithWaterPlants? {
        return dao.getPlantById(id)
    }

    override suspend fun addPlant(plant: Plant) {
        dao.addPlant(plant)
    }

    override suspend fun addWaterPlantToPlant(plant: Plant, waterPlant: WaterPlant) {
        dao.addWaterPlantToPlant(plant, waterPlant)
    }

    override suspend fun deletePlant(plant: Plant) {
        File(plant.picturePath).delete()
        dao.deletePlant(plant)
    }

}