package com.mlallemant.waterplant.feature_plant_list.presentation.plants.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ImageViewer(
    showDialog: Boolean,
    onClose: () -> Unit,
    picturePath: String
) {
    if (showDialog) {
        Dialog(onDismissRequest = onClose) {

            GlideImage(imageModel = picturePath,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(0.5625f)
                    .clickable { onClose() }
                    .clip(RoundedCornerShape(16.dp)),
                loading = {
                    Box(modifier = Modifier.matchParentSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                })


        }
    }
}