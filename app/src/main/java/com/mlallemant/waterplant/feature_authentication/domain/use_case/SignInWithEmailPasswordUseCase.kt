package com.mlallemant.waterplant.feature_authentication.domain.use_case

import com.mlallemant.waterplant.feature_authentication.domain.repository.AuthRepository

class SignInWithEmailPasswordUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (e: Exception) -> Unit
    ) {
        return repository.signInEmailPassword(
            email, password, onSuccess,
            onFailure
        )
    }
}