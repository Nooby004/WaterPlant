package com.mlallemant.waterplant.feature_plant_list.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Plant(

    @PrimaryKey val id: Int? = null,
    val name: String,

    val waterFrequency: String,

    val picturePath: String

)

class InvalidPlantException(message: String) : Exception(message)