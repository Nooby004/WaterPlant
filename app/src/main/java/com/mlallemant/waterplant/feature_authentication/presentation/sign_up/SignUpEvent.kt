package com.mlallemant.waterplant.feature_authentication.presentation.sign_up

sealed class SignUpEvent {

    object SignUpWithEmailPassword : SignUpEvent()
    data class EnteredEmail(val value: String) : SignUpEvent()
    data class EnteredPassword(val value: String) : SignUpEvent()
    data class EnteredConfirmedPassword(val value: String) : SignUpEvent()

}