package com.mlallemant.waterplant.feature_authentication.domain.use_case

import com.mlallemant.waterplant.feature_authentication.domain.model.Response
import com.mlallemant.waterplant.feature_authentication.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class SignInWithEmailPasswordUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Response<Boolean>> {
        return repository.firebaseSignInEmailPassword(email, password)
    }
}