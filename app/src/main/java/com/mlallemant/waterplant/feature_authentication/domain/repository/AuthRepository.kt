package com.mlallemant.waterplant.feature_authentication.domain.repository

interface AuthRepository {
    fun isUserAuthenticatedInFirebase(): Boolean

    suspend fun firebaseSignInEmailPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    )

    suspend fun signOut(
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    )

}