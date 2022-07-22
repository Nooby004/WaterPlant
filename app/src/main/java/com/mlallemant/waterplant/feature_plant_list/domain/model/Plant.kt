package com.mlallemant.waterplant.feature_plant_list.domain.model

data class Plant(

    val id: String = "",

    val name: String = "",

    val waterFrequency: String = "",

    val picturePath: String = "",

    val waterPlants: Map<String, WaterPlant> = hashMapOf()
)
