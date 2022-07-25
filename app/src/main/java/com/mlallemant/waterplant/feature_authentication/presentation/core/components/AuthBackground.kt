package com.mlallemant.waterplant.feature_authentication.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    isButtonEnable: Boolean = true,
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
                .fillMaxHeight(0.7f)
                .fillMaxWidth(0.85f)
                .clip(shape = RoundedCornerShape(20.dp))
                .background(color = MaterialTheme.colors.onBackground),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Box(
                modifier = Modifier
                    .fillMaxWidth(0.30f)
                    .weight(0.8f)
                    .clip(CircleShape)
                    //.border(2.dp, color = MaterialTheme.colors.background, CircleShape)
                    .background(color = MaterialTheme.colors.primary)
                    .aspectRatio(1f)

            ) {

                GlideImage(
                    imageModel = R.mipmap.ic_launcher,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .aspectRatio(1f)
                        .align(Alignment.Center)

                )
            }



            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.4f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextFields()
            }

            Box(
                modifier = Modifier
                    .weight(0.8f)
                    .fillMaxWidth()
            ) {

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = errorText,
                    color = Color.Red,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(16.dp, 0.dp)
                        .fillMaxWidth()
                )


                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(160.dp)
                ) {

                    Button(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth(),
                        enabled = isButtonEnable,
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
}