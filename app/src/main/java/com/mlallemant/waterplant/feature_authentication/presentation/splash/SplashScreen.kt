package com.mlallemant.waterplant.feature_authentication.presentation.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import com.mlallemant.waterplant.R
import com.mlallemant.waterplant.ui.Screen
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.collectLatest


@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()


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
                .padding(padding)
        ) {

            GlideImage(
                imageModel = R.mipmap.ic_launcher,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center)
                    .clip(CircleShape)
            )


        }

    }

}