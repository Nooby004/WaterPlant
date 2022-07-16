package com.mlallemant.waterplant.feature_authentication.presentation.auth

import com.mlallemant.waterplant.feature_authentication.domain.model.Response

data class AuthState(
    val isUserAuthenticated: Boolean = false,
    val signInState: Response<Boolean> = Response.Success(false)
)