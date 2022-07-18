package com.mlallemant.waterplant.feature_authentication.domain.repository

interface AuthRepository {
    fun isUserAuthenticatedInFirebase(): Boolean

    suspend fun signInEmailPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (e: Exception) -> Unit
    )

    suspend fun signUpEmailPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (e: Exception) -> Unit
    )

    suspend fun signOut(
        onSuccess: () -> Unit,
        onFailure: (e: Exception) -> Unit
    )

}