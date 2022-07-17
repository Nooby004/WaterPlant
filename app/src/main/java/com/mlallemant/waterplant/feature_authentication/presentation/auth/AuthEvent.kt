package com.mlallemant.waterplant.feature_authentication.presentation.auth

sealed class AuthEvent {

    object SignInWithEmailPassword : AuthEvent()
    data class EnteredEmail(val value: String) : AuthEvent()
    data class EnteredPassword(val value: String) : AuthEvent()

}
