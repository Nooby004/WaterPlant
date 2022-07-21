package com.mlallemant.waterplant.feature_authentication.presentation.sign_up

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlallemant.waterplant.feature_authentication.domain.extension.isEmailValid
import com.mlallemant.waterplant.feature_authentication.domain.extension.isPasswordStrongEnough
import com.mlallemant.waterplant.feature_authentication.domain.use_case.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _state = mutableStateOf(SignUpState())
    val state: State<SignUpState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun onSuccessSignUp() {
        viewModelScope.launch {
            _state.value = state.value.copy(
                loading = false
            )

            _eventFlow.emit(UiEvent.SignUpSuccess)
        }
    }

    private fun onFailureSignUp(e: Exception) {

        var detailMessage = ""
        e.message?.let {
            detailMessage = it
        }

        viewModelScope.launch {
            _state.value = state.value.copy(
                loading = false,
                error = detailMessage
            )

        }
    }

    private fun checkEnablingButton() {
        _state.value = state.value.copy(
            enable = isButtonEnable()
        )
    }

    private fun isButtonEnable(): Boolean {
        return state.value.email.isEmailValid()
                && state.value.password.isPasswordStrongEnough()
                && state.value.password == state.value.confirmedPassword
    }

    fun onEvent(event: SignUpEvent) {

        when (event) {
            is SignUpEvent.SignUpWithEmailPassword -> {
                viewModelScope.launch {
                    _state.value = state.value.copy(
                        loading = true
                    )
                    
                    authUseCases.signUpWithEmailPassword(
                        state.value.email,
                        state.value.password,
                        onSuccess = { onSuccessSignUp() },
                        onFailure = {
                            onFailureSignUp(it)
                        }
                    )
                }

            }

            is SignUpEvent.EnteredEmail -> {
                _state.value = state.value.copy(
                    email = event.value,
                    emailError = if (event.value.isEmailValid()) "" else "Email is not valid"
                )
                checkEnablingButton()
            }
            is SignUpEvent.EnteredPassword -> {
                _state.value = state.value.copy(
                    password = event.value,
                    passwordError = if (event.value.isPasswordStrongEnough()) "" else "Must have at least 6 characters and 1 number"
                )
                checkEnablingButton()
            }
            is SignUpEvent.EnteredConfirmedPassword -> {
                _state.value = state.value.copy(
                    confirmedPassword = event.value,
                    confirmedPasswordError = if (event.value == state.value.password) "" else "Passwords are different"
                )
                checkEnablingButton()
            }
        }
    }
}


sealed class UiEvent {
    object SignUpSuccess : UiEvent()
}
