package com.mlallemant.waterplant.feature_authentication.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.mlallemant.waterplant.R
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun AuthBackground(
    modifier: Modifier = Modifier,
    TopText: @Composable () -> Unit,
    TextFields: @Composable () -> Unit,
    errorText: String = "",
    buttonText: String,
    isLoading: Boolean,
    onClickButton: () -> Unit,
    BottomText: @Composable () -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {

        Column(Modifier.fillMaxSize()) {

            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(color = MaterialTheme.colors.primaryVariant),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // top green block
                TopText()

            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(color = MaterialTheme.colors.background),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // bottom gray block
                BottomText()

            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(50.dp, 180.dp)
                .clip(shape = RoundedCornerShape(30.dp))
                .background(color = MaterialTheme.colors.onBackground),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            GlideImage(
                imageModel = R.mipmap.ic_launcher,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(120.dp, 0.dp)
                    .aspectRatio(1f)

            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.2f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextFields()
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .width(160.dp)
            ) {

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = errorText,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.TopCenter)
                )

                Button(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth(),
                    content = {
                        Text(text = buttonText)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.background,
                        contentColor = MaterialTheme.colors.primary
                    ),
                    onClick = {
                        onClickButton()
                    })

                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(30.dp)
                            .padding(4.dp),
                        color = MaterialTheme.colors.primary
                    )
                }
            }

        }
    }
}