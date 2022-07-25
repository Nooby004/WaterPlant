package com.mlallemant.waterplant.feature_plant_list.presentation.add_edit_plant.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun PlantImageView(
    modifier: Modifier = Modifier,
    picturePath: String? = "",
    onClick: () -> Unit,
) {

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color = MaterialTheme.colors.primary)
            .border(
                BorderStroke(2.dp, color = MaterialTheme.colors.primaryVariant),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable {
                onClick()
            }
    ) {

        if (picturePath.isNullOrEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AddAPhoto,
                    contentDescription = "Add plant photo",
                    tint = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier.scale(2f)
                )
            }
        } else {

            GlideImage(
                imageModel = picturePath,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp)),
                shimmerParams = ShimmerParams(
                    baseColor = MaterialTheme.colors.background,
                    highlightColor = MaterialTheme.colors.primaryVariant,
                    durationMillis = 350,
                    dropOff = 0.65f,
                    tilt = 20f
                ),
            )


        }

    }
}