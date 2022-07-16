package com.mlallemant.waterplant.feature_authentication.domain.use_case

import com.mlallemant.waterplant.feature_authentication.domain.repository.AuthRepository

class IsUserAuthenticatedUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke() = repository.isUserAuthenticatedInFirebase()
}