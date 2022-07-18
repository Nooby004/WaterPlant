package com.mlallemant.waterplant.feature_authentication.presentation.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlallemant.waterplant.feature_authentication.domain.model.ErrorType
import com.mlallemant.waterplant.feature_authentication.domain.model.Response
import com.mlallemant.waterplant.feature_authentication.domain.use_case.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _state = mutableStateOf(AuthState())
    val state: State<AuthState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    private fun onSuccessLogin() {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.SignInResult(Response.Success(true)))
            _state.value = state.value.copy(
                password = "",
                error = ""
            )
        }
    }

    private fun onFailureLogin(e: Exception) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                error = "Bad email/password"
            )
            _eventFlow.emit(UiEvent.SignInResult(Response.Error(ErrorType.BAD_LOGIN.name)))
        }
    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SignInWithEmailPassword -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.SignInResult(Response.Loading))

                    authUseCases.signInWithEmailPassword(
                        state.value.email,
                        state.value.password,
                        onSuccess = { onSuccessLogin() },
                        onFailure = ::onFailureLogin
                    )
                }
            }

            is AuthEvent.EnteredEmail -> {
                _state.value = state.value.copy(
                    email = event.value
                )
            }

            is AuthEvent.EnteredPassword -> {
                _state.value = state.value.copy(
                    password = event.value
                )
            }
        }
    }
}

sealed class UiEvent {
    data class SignInResult(val response: Response<Boolean>) : UiEvent()
}