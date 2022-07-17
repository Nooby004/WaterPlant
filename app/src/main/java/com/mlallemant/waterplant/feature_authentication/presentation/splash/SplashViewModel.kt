package com.mlallemant.waterplant.feature_authentication.presentation.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlallemant.waterplant.feature_authentication.domain.use_case.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _state = mutableStateOf(SplashState())
    val state: State<SplashState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {
        isAuthenticated()
    }


    private fun isAuthenticated() {
        viewModelScope.launch {

            authUseCases.isUserAuthenticated().let {
                Timber.d("The user is authenticated : %s", it)

                _state.value = state.value.copy(
                    isUserAuthenticated = it,
                )

                delay(2000)
                _eventFlow.emit(UiEvent.UserAlreadyAuthenticated(it))
            }
        }
    }

}


sealed class UiEvent {
    data class UserAlreadyAuthenticated(val value: Boolean) : UiEvent()
}