package com.mlallemant.waterplant.feature_plant_list.presentation.plants.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
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
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.onBackground)
                    .combinedClickable(
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                        onClick = { onClick() },
                        onLongClick = {
                            onOpenEdit(plant.id)
                        }
                    ),
                verticalArrangement = Arrangement.Top,
                Alignment.CenterHorizontally
            ) {


                GlideImage(
                    imageModel = plant.picturePath,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(20.dp))
                        .aspectRatio(1f),
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
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.background,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }
        }
    }

}


@Composable
fun Triangle(
    colorBackground: Color
) {
    androidx.compose.foundation.Canvas(
        modifier = Modifier
            .fillMaxSize(0.7f)
            .aspectRatio(1f)

    ) {
        val rect = Rect(Offset.Zero, size)
        val trianglePath = Path().apply {
            moveTo(rect.topCenter)
            lineTo(rect.bottomRight)
            lineTo(rect.bottomLeft)
            close()
        }

        drawIntoCanvas { canvas ->
            canvas.drawOutline(
                outline = Outline.Generic(trianglePath),
                paint = Paint().apply {
                    color = colorBackground
                    pathEffect = PathEffect.cornerPathEffect(rect.maxDimension / 3)
                }
            )
        }
    }
}

fun Path.moveTo(offset: Offset) = moveTo(offset.x, offset.y)
fun Path.lineTo(offset: Offset) = lineTo(offset.x, offset.y)