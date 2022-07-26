package com.mlallemant.waterplant.feature_plant_list.domain.use_case

data class PlantUseCases(
    val getPlants: GetPlantsUseCase,
    val getPlant: GetPlantUseCase,
    val savePlant: SavePlantUseCase,
    val addWaterToPlant: AddWaterToPlantUseCase,
    val deletePlant: DeletePlantUseCase
)