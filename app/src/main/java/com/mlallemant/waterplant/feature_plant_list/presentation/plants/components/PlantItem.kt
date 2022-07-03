package com.mlallemant.waterplant.feature_plant_list.presentation.plants.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mlallemant.waterplant.R
import com.mlallemant.waterplant.feature_plant_list.domain.model.PlantWithWaterPlants

@Composable
fun PlantItem(
    plantWithWaterPlant: PlantWithWaterPlants,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = modifier
    ) {

        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.primaryVariant)
                    .padding(16.dp)
                    .padding(end = 32.dp)
            ) {
                Text(
                    text = plantWithWaterPlant.plant.name,
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.background,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (plantWithWaterPlant.waterPlants.isNotEmpty()) {
                    Image(
                        painter = painterResource(
                            R.drawable.ic_launcher_foreground
                        ),
                        contentDescription = "Last photo of the plant"
                    )
                } else {
                    Image(
                        painter = painterResource(
                            R.drawable.ic_launcher_foreground
                        ),
                        contentDescription = "Last photo of the plant"
                    )
                }
            }

            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete note"
                )

            }
        }


    }

}

