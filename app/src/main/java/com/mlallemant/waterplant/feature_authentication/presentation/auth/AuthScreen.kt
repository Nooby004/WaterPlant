package com.mlallemant.waterplant.feature_authentication.presentation.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.request.RequestOptions
import com.mlallemant.waterplant.R
import com.mlallemant.waterplant.feature_authentication.domain.model.Response
import com.mlallemant.waterplant.feature_authentication.presentation.auth.components.AuthTextField
import com.mlallemant.waterplant.ui.Screen
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.collectLatest


@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    val email = viewModel.email.collectAsState().value
    val password = viewModel.password.collectAsState().value

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.SignInSuccess -> {
                    navController.navigate(Screen.PlantsScreen.route)
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
        ) {

            if (state.signInState == Response.Loading) {
                CircularProgressIndicator()
            } else {


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    GlideImage(
                        imageModel = R.mipmap.ic_launcher,
                        contentScale = ContentScale.Crop,
                        requestOptions = {
                            RequestOptions()
                                .circleCrop()
                        },
                        modifier = Modifier

                            .fillMaxWidth()
                            .padding(100.dp, 0.dp)
                            .aspectRatio(1f)

                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceAround,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AuthTextField(
                            "Email",
                            email,
                            keyboardType = KeyboardType.Email,
                            onValueChange = { viewModel.onEvent(AuthEvent.EnteredEmail(it)) }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        AuthTextField(
                            "Password",
                            password,
                            keyboardType = KeyboardType.Password,
                            onValueChange = { viewModel.onEvent(AuthEvent.EnteredPassword(it)) }
                        )

                    }

                    Button(content = {
                        Text(text = "Sign In")
                    },
                        onClick = {
                            viewModel.onEvent(
                                AuthEvent.SignInWithEmailPassword(
                                    email,
                                    password
                                )
                            )
                        })

                }


            }


        }

    }

}