package com.mlallemant.waterplant.feature_plant_list.domain.model

import androidx.room.Embedded
import androidx.room.Relation

data class PlantWithWaterPlants(

    @Embedded val plant: Plant,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val waterPlants: List<WaterPlant>
)
