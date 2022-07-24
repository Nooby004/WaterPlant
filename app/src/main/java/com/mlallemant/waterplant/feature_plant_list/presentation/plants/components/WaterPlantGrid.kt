package com.mlallemant.waterplant.feature_plant_list.presentation.plants.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        columns = GridCells.Fixed(2),
    ) {
        itemsIndexed(waterPlants) { index, waterPlant ->

            Row(Modifier.height(IntrinsicSize.Min)) {

                if ((index + 1) % 2 == 0) {
                    Column() {
                        Divider(
                            color = MaterialTheme.colors.onBackground,
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(4.dp)
                        )
                    }
                }


                Column(
                    Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    Divider(
                        color = MaterialTheme.colors.onBackground,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                    )

                    Box(
                        Modifier
                            .fillMaxSize()
                    ) {

                        GlideImage(
                            imageModel = waterPlant.picturePath,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clickable { onItemClick(waterPlant.picturePath) }
                                .aspectRatio(0.8f)
                                .clip(RoundedCornerShape(20.dp)),
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
                                .clip(RoundedCornerShape(20.dp))
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
                    
                }



                if ((index + 1) % 2 != 0) {

                    Column() {
                        Divider(
                            color = MaterialTheme.colors.onBackground,
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(4.dp)
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