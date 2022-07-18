package com.mlallemant.waterplant.feature_authentication.domain.use_case

import com.mlallemant.waterplant.feature_authentication.domain.repository.AuthRepository

class SignOutUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(
        onSuccess: () -> Unit,
        onFailure: (e: Exception) -> Unit
    ) = repository.signOut(onSuccess, onFailure)
}