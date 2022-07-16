package com.mlallemant.waterplant.feature_authentication.presentation.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlallemant.waterplant.feature_authentication.domain.model.Response
import com.mlallemant.waterplant.feature_authentication.domain.use_case.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {
    private val _state = mutableStateOf(AuthState())
    val state: State<AuthState> = _state

    private val _email: MutableStateFlow<String> = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password: MutableStateFlow<String> = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        isAuthenticated()
    }

    private fun isAuthenticated() {
        viewModelScope.launch {
            authUseCases.isUserAuthenticated().let {
                _state.value = state.value.copy(
                    isUserAuthenticated = it
                )

                if (_state.value.isUserAuthenticated) {
                    _eventFlow.emit(UiEvent.SignInSuccess)
                }
            }
        }
    }


    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SignInWithEmailPassword -> {
                viewModelScope.launch {
                    authUseCases.signInWithEmailPassword(event.email, event.password).collect {
                        _state.value = state.value.copy(
                            signInState = it,
                            isUserAuthenticated = it == Response.Success(true)
                        )

                        if (_state.value.isUserAuthenticated) {
                            _eventFlow.emit(UiEvent.SignInSuccess)
                        }

                    }
                }
            }

            is AuthEvent.SignOut -> {
                viewModelScope.launch {
                    authUseCases.signOut().collect {
                        _state.value = state.value.copy(
                            signInState = it,
                            isUserAuthenticated = false
                        )
                    }
                }
            }

            is AuthEvent.EnteredEmail -> {
                _email.value = event.value
            }

            is AuthEvent.EnteredPassword -> {
                _password.value = event.value
            }
        }
    }
}

sealed class UiEvent {
    object SignInSuccess : UiEvent()
}