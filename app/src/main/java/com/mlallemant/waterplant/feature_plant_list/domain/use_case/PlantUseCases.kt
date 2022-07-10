package com.mlallemant.waterplant.feature_plant_list.domain.use_case

data class PlantUseCases(
    val getPlantsWithWaterPlants: GetPlantsWithWaterPlantsUseCase,
    val getPlantWithWaterPlants: GetPlantWithWaterPlantsUseCase,
    val addPlant: AddPlantUseCase,
    val addWaterToPlant: AddWaterToPlantUseCase,
    val getNextWateringUseCase: GetNextWateringUseCase,
    val deletePlant: DeletePlantUseCase
)