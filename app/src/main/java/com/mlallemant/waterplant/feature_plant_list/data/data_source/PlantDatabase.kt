package com.mlallemant.waterplant.feature_plant_list.data.data_source

import androidx.room.RoomDatabase

abstract class  PlantDatabase : RoomDatabase() {

    abstract val plantDao : PlantDao

    companion object {
        const val DATABASE_NAME = "plants_db"
    }
}