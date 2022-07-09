package com.mlallemant.waterplant.feature_plant_list.presentation.plants.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mlallemant.waterplant.feature_plant_list.domain.model.PlantWithWaterPlants
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PlantItem(
    plantWithWaterPlant: PlantWithWaterPlants,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {

        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(20.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.primaryVariant)
                    .padding(16.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = plantWithWaterPlant.plant.name,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.background,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    IconButton(
                        onClick = {
                            onEdit()
                        },
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit plant",
                            tint = MaterialTheme.colors.background,
                        )
                    }
                }


                Spacer(modifier = Modifier.height(10.dp))

                GlideImage(
                    imageModel = plantWithWaterPlant.plant.picturePath,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(20.dp)),
                    loading = {
                        Box(modifier = Modifier.matchParentSize()) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    },
                )

            }


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp, 0.dp, 0.dp, 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {

                OutlinedButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .size(80.dp),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),  //avoid the little icon
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colors.background,
                        backgroundColor = MaterialTheme.colors.background
                    )
                ) {
                    Icon(
                        Icons.Default.WaterDrop,
                        contentDescription = "Water plant",
                        tint = MaterialTheme.colors.secondary,
                        modifier = Modifier.scale(2f)
                    )
                }


            }


        }


    }

}

