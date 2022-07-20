package com.mlallemant.waterplant.feature_authentication.presentation.sign_up

data class SignUpState(
    val email: String = "",
    val emailError: String = "",
    val password: String = "",
    val passwordError: String = "",
    val confirmedPassword: String = "",
    val confirmedPasswordError: String = "",
    val error: String = "",
    val enable: Boolean = false,
    val loading: Boolean = false
)
