package com.mlallemant.waterplant.feature_authentication.domain.use_case

import com.mlallemant.waterplant.feature_authentication.domain.repository.AuthRepository

class SignOutUseCase(
    private val repository: AuthRepository
) {

    suspend operator fun invoke() = repository.signOut()
}