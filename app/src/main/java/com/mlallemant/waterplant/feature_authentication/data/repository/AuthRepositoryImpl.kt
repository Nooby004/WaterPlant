package com.mlallemant.waterplant.feature_authentication.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.mlallemant.waterplant.feature_authentication.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override fun isUserAuthenticatedInFirebase() = auth.currentUser != null

    override suspend fun firebaseSignInEmailPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure()
                }
        } catch (e: Exception) {
            onFailure()
        }
    }

    override suspend fun signOut(
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        try {
            val authStateListener = FirebaseAuth.AuthStateListener { auth ->
                if (auth.currentUser == null) {
                    onSuccess()
                } else {
                    onFailure()
                }
            }
            auth.addAuthStateListener(authStateListener)
            auth.signOut()

        } catch (e: Exception) {
            onFailure()
        }
    }
}