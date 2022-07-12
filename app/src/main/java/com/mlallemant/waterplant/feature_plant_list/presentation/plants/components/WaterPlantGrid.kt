package com.mlallemant.waterplant.feature_plant_list.presentation.plants.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.mlallemant.waterplant.feature_plant_list.domain.model.WaterPlant
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun WaterPlantGrid(
    waterPlants: List<WaterPlant>,
    onItemClick: (String) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3)
    ) {
        items(waterPlants.size) { index ->

            Row(Modifier.height(IntrinsicSize.Min)) {

                Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    GlideImage(
                        imageModel = waterPlants[index].picturePath,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clickable { onItemClick(waterPlants[index].picturePath) }
                            .aspectRatio(1f),
                        loading = {
                            Box(modifier = Modifier.matchParentSize()) {
                                CircularProgressIndicator(
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        },
                    )

                    Divider(color = MaterialTheme.colors.background, thickness = 2.dp)
                }

                if ((index + 1) % 3 != 0) {
                    Column() {
                        Divider(
                            color = MaterialTheme.colors.background,
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(2.dp)
                        )
                    }
                }
            }
        }
    }

}