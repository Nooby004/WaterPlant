package com.mlallemant.waterplant.feature_authentication.domain.repository

import com.mlallemant.waterplant.feature_authentication.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun isUserAuthenticatedInFirebase(): Boolean

    suspend fun firebaseSignInEmailPassword(
        email: String,
        password: String
    ): Flow<Response<Boolean>>

    suspend fun signOut(): Flow<Response<Boolean>>

    fun getFirebaseAuthState(): Flow<Boolean>
}