package com.mlallemant.waterplant.feature_plant_list.presentation.plants.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.mlallemant.waterplant.R
import com.mlallemant.waterplant.feature_plant_list.domain.model.Plant
import com.skydoves.landscapist.glide.GlideImage


@ExperimentalFoundationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StickyPlantsHeader(
    onAddNewPlantClick: () -> Unit,
    plants: List<Plant>,
    lastPlantIdClicked: String,
    onEditPlant: (id: String) -> Unit,
    onSelectPlant: (id: String) -> Unit,
    nextWatering: Long,
    currentPlant: Plant?

) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                onAddNewPlantClick()
            },
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.1f)
                .padding(12.dp),
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.primaryVariant
            )
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "content description",
                tint = MaterialTheme.colors.primaryVariant
            )
            Text(text = "Add new plant", style = MaterialTheme.typography.subtitle2)
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            items(plants) { plant ->
                if (lastPlantIdClicked == "-1") {
                    onSelectPlant(plant.id)
                }

                Spacer(modifier = Modifier.width(12.dp))

                PlantItem(
                    plant = plant,
                    onClick = {
                        onSelectPlant(plant.id)
                    },
                    isSelected = lastPlantIdClicked == plant.id,
                    onOpenEdit = { plantId ->
                        onEditPlant(plantId)
                    },
                    modifier = Modifier
                        .width(100.dp)
                        .height(140.dp)

                )

                if (plant.id == plants.last().id) {
                    Spacer(modifier = Modifier.width(12.dp))
                }
            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp, 0.dp)
        ) {


            if (nextWatering != -1L) {

                Column(modifier = Modifier.fillMaxWidth()) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {


                        GlideImage(
                            imageModel = R.mipmap.watering_can,
                            contentScale = ContentScale.Inside,
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.secondary),
                            modifier = Modifier
                                .size(40.dp)
                        )

                        Text(
                            text = when (nextWatering) {
                                0L -> "Please, water the plant today !"
                                1L -> "Next watering in $nextWatering day"
                                else -> "Next watering in $nextWatering days"
                            },
                            modifier = Modifier
                                .padding(8.dp),
                            style = MaterialTheme.typography.body1,
                            color = when (nextWatering) {
                                0L -> MaterialTheme.colors.secondary
                                else -> MaterialTheme.colors.background
                            }
                        )
                    }

                    currentPlant?.name?.let { it ->

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = if (currentPlant.waterPlants.isEmpty()) "No watering history for" else "Watering history of",
                                style = MaterialTheme.typography.h6,
                                color = MaterialTheme.colors.background
                            )

                            Text(
                                text = " $it",
                                style = MaterialTheme.typography.h6,
                                color = MaterialTheme.colors.primaryVariant
                            )
                        }
                    }
                }
            }
        }
    }
}