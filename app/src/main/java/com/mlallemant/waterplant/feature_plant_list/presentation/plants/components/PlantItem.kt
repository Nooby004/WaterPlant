package com.mlallemant.waterplant.feature_plant_list.presentation.plants.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mlallemant.waterplant.feature_plant_list.domain.model.Plant
import com.skydoves.landscapist.glide.GlideImage

@ExperimentalFoundationApi
@Composable
fun PlantItem(
    plant: Plant,
    onOpenEdit: (plantId: String) -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    Box(
        modifier = modifier
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            Arrangement.Center,
            Alignment.CenterHorizontally
        ) {


            GlideImage(
                imageModel = plant.picturePath,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .run {
                        if (isSelected) {
                            border(
                                2.dp,
                                color = MaterialTheme.colors.primaryVariant,
                                CircleShape
                            )
                        } else {
                            this
                        }
                    }
                    .aspectRatio(1f)
                    .weight(0.8f)
                    .combinedClickable(
                        onClick = { onClick() },
                        onLongClick = {
                            onOpenEdit(plant.id)
                        }
                    ),
                loading = {
                    Box(modifier = Modifier.matchParentSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = plant.name,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.primaryVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }

    }

}

