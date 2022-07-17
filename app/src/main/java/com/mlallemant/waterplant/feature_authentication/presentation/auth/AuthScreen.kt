package com.mlallemant.waterplant.feature_authentication.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mlallemant.waterplant.R
import com.mlallemant.waterplant.feature_authentication.domain.model.Response
import com.mlallemant.waterplant.feature_authentication.presentation.auth.components.AuthPasswordTextField
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

    var isLoginLoading by remember() { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.SignInResult -> {
                    when (event.response) {
                        is Response.Success -> {
                            isLoginLoading = false
                            if (event.response.data) {
                                navController.navigate(Screen.PlantsScreen.route)
                            }
                            // else show toast error authen
                        }
                        is Response.Loading -> {
                            isLoginLoading = true
                        }
                        is Response.Error -> {
                            isLoginLoading = false
                        }
                    }
                }

                is UiEvent.AlreadyAuthenticated -> {
                    if (event.value) {
                        navController.navigate(Screen.PlantsScreen.route)
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

            if (state.userAlreadyAuthenticatedLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                Column(Modifier.fillMaxSize()) {

                    Column(
                        Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .background(color = MaterialTheme.colors.primaryVariant),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        // top green block
                        Text(
                            text = "Water Plant",
                            modifier = Modifier.padding(0.dp, 60.dp),
                            style = MaterialTheme.typography.h4,
                            color = MaterialTheme.colors.background
                        )

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

                        Row {
                            Text(
                                text = "Don't have an account ? ",
                                modifier = Modifier.padding(0.dp, 80.dp),
                                style = MaterialTheme.typography.body1,
                                color = MaterialTheme.colors.primary
                            )


                            Text(
                                text = "Sign Up",
                                modifier = Modifier.padding(0.dp, 80.dp),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.body1,
                                color = MaterialTheme.colors.primaryVariant
                            )

                        }

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
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AuthTextField(
                            "Email",
                            state.email,
                            keyboardType = KeyboardType.Email,
                            onValueChange = { viewModel.onEvent(AuthEvent.EnteredEmail(it)) }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        AuthPasswordTextField(
                            "Password",
                            state.password,
                            onValueChange = { viewModel.onEvent(AuthEvent.EnteredPassword(it)) }
                        )


                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .width(160.dp)
                    ) {

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = state.error,
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.TopCenter)
                        )

                        Button(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxWidth(),
                            content = {
                                Text(text = "Login")
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.background,
                                contentColor = MaterialTheme.colors.primary
                            ),
                            onClick = {
                                viewModel.onEvent(
                                    AuthEvent.SignInWithEmailPassword
                                )
                            })

                        if (isLoginLoading) {
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

}