package com.mlallemant.waterplant.feature_authentication.presentation.auth

data class AuthState(
    val isUserAuthenticated: Boolean = false,
    val email: String = "",
    val password: String = "",
    val error: String = "",
    val userAlreadyAuthenticatedLoading: Boolean = false
)