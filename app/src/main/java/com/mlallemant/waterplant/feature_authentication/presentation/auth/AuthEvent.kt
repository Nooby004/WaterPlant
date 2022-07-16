package com.mlallemant.waterplant.feature_authentication.presentation.auth

sealed class AuthEvent {

    data class SignInWithEmailPassword(val email: String, val password: String) : AuthEvent()
    object SignOut : AuthEvent()
    data class EnteredEmail(val value: String) : AuthEvent()
    data class EnteredPassword(val value: String) : AuthEvent()

}
