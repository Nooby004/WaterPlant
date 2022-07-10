package com.mlallemant.waterplant.feature_plant_list.domain.use_case

data class PlantUseCases(
    val getPlants: GetPlantsUseCase,
    val getPlantWithWaterPlants: GetPlantWithWaterPlantsUseCase,
    val addPlant: AddPlantUseCase,
    val addWaterToPlant: AddWaterToPlantUseCase,
    val getNextWatering: GetNextWateringUseCase,
    val deletePlant: DeletePlantUseCase
)