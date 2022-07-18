package com.mlallemant.waterplant.feature_authentication.presentation.sign_up

import androidx.lifecycle.ViewModel
import com.mlallemant.waterplant.feature_authentication.domain.use_case.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {
}
