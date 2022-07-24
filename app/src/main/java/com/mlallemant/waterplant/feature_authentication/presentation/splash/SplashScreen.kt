package com.mlallemant.waterplant.feature_authentication.presentation.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mlallemant.waterplant.R
import com.mlallemant.waterplant.ui.Screen
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.collectLatest


@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {

    val scaffoldState = rememberScaffoldState()

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.background)


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.UserAlreadyAuthenticated -> {
                    if (event.value) {
                        navController.navigate(Screen.PlantsScreen.route)
                    } else {
                        navController.navigate(Screen.AuthScreen.route)
                    }
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
                .padding(0.dp)
        ) {

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(150.dp)
                    .align(Alignment.Center)
                    .background(MaterialTheme.colors.primary)
            )

            GlideImage(
                imageModel = R.mipmap.ic_launcher,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(160.dp, 0.dp)
                    .aspectRatio(1f)
                    .align(Alignment.Center)
            )

            /*    GlideImage(
                    imageModel = R.mipmap.ic_launcher,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .align(Alignment.Center)
                        .clip(CircleShape)
                )*/


        }

    }

}