package com.mlallemant.waterplant.feature_plant_list.data.data_source

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.mlallemant.waterplant.feature_plant_list.domain.model.Plant
import com.mlallemant.waterplant.feature_plant_list.domain.model.PlantWithWaterPlants
import com.mlallemant.waterplant.feature_plant_list.domain.model.WaterPlant
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {

    @Transaction
    @Query(value = "SELECT * FROM Plant")
    fun getPlantsWithWaterPlants(): Flow<List<PlantWithWaterPlants>>

    @Transaction
    @Query(value = "SELECT * FROM Plant where id =:id")
    suspend fun getPlantById(id: Int): PlantWithWaterPlants?

    @Insert(onConflict = REPLACE)
    suspend fun addPlant(plant: Plant)

    @Insert(onConflict = REPLACE)
    suspend fun addWaterPlant(waterPlant: WaterPlant)

    @Delete
    suspend fun deletePlant(plant: Plant)

    @Transaction
    @Query(value = "SELECT picturePath FROM WaterPlant WHERE plantId =:plantId")
    suspend fun getAllWaterPlantPathPicture(plantId: Int): List<String>

    @Transaction
    @Query(value = "DELETE FROM WaterPlant WHERE plantId =:plantId")
    suspend fun deleteAllWaterPlant(plantId: Int)
}