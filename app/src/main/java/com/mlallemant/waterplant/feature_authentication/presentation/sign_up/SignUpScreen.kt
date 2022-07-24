package com.mlallemant.waterplant.feature_authentication.presentation.sign_up

import android.widget.Toast
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mlallemant.waterplant.feature_authentication.presentation.core.components.AuthBackground
import com.mlallemant.waterplant.feature_authentication.presentation.core.components.AuthPasswordTextField
import com.mlallemant.waterplant.feature_authentication.presentation.core.components.AuthTextField
import kotlinx.coroutines.flow.collectLatest


@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = MaterialTheme.colors.primaryVariant)
    systemUiController.setNavigationBarColor(color = MaterialTheme.colors.background)

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->

            when (event) {
                is UiEvent.SignUpSuccess -> {
                    navController.popBackStack()

                    Toast.makeText(
                        context,
                        "Account successfully created !",
                        Toast.LENGTH_SHORT
                    ).show()
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
                    hint = "Email",
                    value = state.email,
                    error = state.emailError,
                    keyboardType = KeyboardType.Email,
                    onValueChange = {
                        viewModel.onEvent(SignUpEvent.EnteredEmail(it))
                    }
                )

                Spacer(modifier = Modifier.height(2.dp))

                AuthPasswordTextField(
                    hint = "Password",
                    value = state.password,
                    error = state.passwordError,
                    onValueChange = {
                        viewModel.onEvent(SignUpEvent.EnteredPassword(it))
                    }
                )
                Spacer(modifier = Modifier.height(2.dp))

                AuthPasswordTextField(
                    hint = "Confirm password",
                    value = state.confirmedPassword,
                    error = state.confirmedPasswordError,
                    onValueChange = {
                        viewModel.onEvent(SignUpEvent.EnteredConfirmedPassword(it))
                    }
                )

            },
            buttonText = "Sign Up",
            errorText = state.error,
            isLoading = state.loading,
            isButtonEnable = state.enable,
            onClickButton = {
                viewModel.onEvent(
                    SignUpEvent.SignUpWithEmailPassword
                )
            },
            BottomText = { }
        )
    }

}