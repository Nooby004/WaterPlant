package com.mlallemant.waterplant.feature_plant_list.presentation.plants.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.mlallemant.waterplant.feature_plant_list.domain.model.WaterPlant
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun WaterPlantGrid(
    waterPlants: List<WaterPlant>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3)
    ) {
        items(waterPlants.size) { index ->

            GlideImage(
                imageModel = waterPlants[index].picturePath,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f),
                loading = {
                    Box(modifier = Modifier.matchParentSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
            )
        }
    }

}