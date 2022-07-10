package com.mlallemant.waterplant.feature_plant_list.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WaterPlant(

    @PrimaryKey val id: Int? = null,
    val picturePath: String,
    val timestamp: Long,
    val plantId: Int

)
