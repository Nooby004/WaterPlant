package com.mlallemant.waterplant.feature_plant_list.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mlallemant.waterplant.feature_plant_list.domain.model.Plant
import com.mlallemant.waterplant.feature_plant_list.domain.model.WaterPlant

@Database(
    entities = [Plant::class, WaterPlant::class],
    version = 1
)
abstract class PlantDatabase : RoomDatabase() {

    abstract val plantDao: PlantDao

    companion object {
        const val DATABASE_NAME = "plants_db"
    }
}