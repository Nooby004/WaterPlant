package com.mlallemant.waterplant.feature_plant_list.presentation.plants.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.mlallemant.waterplant.feature_plant_list.domain.model.WaterPlant
import com.skydoves.landscapist.glide.GlideImage
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WaterPlantGrid(
    waterPlants: List<WaterPlant> = emptyList(),
    onItemClick: (String) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3)
    ) {
        itemsIndexed(waterPlants) { index, waterPlant ->

            Row(Modifier.height(IntrinsicSize.Min)) {

                Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {

                    Box(Modifier.fillMaxSize()) {

                        GlideImage(
                            imageModel = waterPlant.picturePath,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clickable { onItemClick(waterPlant.picturePath) }
                                .aspectRatio(1f),
                            loading = {
                                Box(modifier = Modifier.matchParentSize()) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            },
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.3f)
                                .align(Alignment.BottomCenter)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black
                                        )
                                    )
                                )

                        )


                        Text(
                            text = getDateTime(waterPlant.timestamp),
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(8.dp),
                            color = MaterialTheme.colors.primary,
                            style = MaterialTheme.typography.body2
                        )


                    }

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

private fun getDateTime(timestamp: Long): String {
    return try {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
        val netDate = Date(timestamp)
        sdf.format(netDate)
    } catch (e: Exception) {
        e.toString()
    }
}