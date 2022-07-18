package com.mlallemant.waterplant.feature_authentication.presentation.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mlallemant.waterplant.feature_authentication.domain.model.Response
import com.mlallemant.waterplant.feature_authentication.presentation.core.components.AuthBackground
import com.mlallemant.waterplant.feature_authentication.presentation.core.components.AuthPasswordTextField
import com.mlallemant.waterplant.feature_authentication.presentation.core.components.AuthTextField
import com.mlallemant.waterplant.ui.Screen
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
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) { padding ->

        AuthBackground(
            modifier = Modifier.padding(padding),
            TopText = {
                Text(
                    text = "Water Plant",
                    modifier = Modifier.padding(0.dp, 60.dp),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.background
                )
            },
            TextFields = {
                AuthTextField(
                    "Email",
                    state.email,
                    keyboardType = KeyboardType.Email,
                    onValueChange = { viewModel.onEvent(AuthEvent.EnteredEmail(it)) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                AuthPasswordTextField(
                    "Password",
                    state.password,
                    onValueChange = { viewModel.onEvent(AuthEvent.EnteredPassword(it)) }
                )

            },
            buttonText = "Login",
            errorText = state.error,
            isLoading = isLoginLoading,
            onClickButton = {
                viewModel.onEvent(
                    AuthEvent.SignInWithEmailPassword
                )
            },
            BottomText = {
                Row {
                    Text(
                        text = "Don't have an account ? ",
                        modifier = Modifier.padding(0.dp, 80.dp),
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.primary
                    )


                    Text(
                        text = "Sign Up",
                        modifier = Modifier
                            .padding(0.dp, 80.dp)
                            .clickable {
                                navController.navigate(Screen.SignUpScreen.route)
                            },
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.primaryVariant
                    )

                }
            })
    }

}