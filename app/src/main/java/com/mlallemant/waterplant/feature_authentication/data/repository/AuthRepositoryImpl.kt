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

    override suspend fun signInEmailPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (e: Exception) -> Unit
    ) {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure(it)
                }
        } catch (e: Exception) {
            onFailure(e)
        }
    }


    override suspend fun signUpEmailPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (e: Exception) -> Unit
    ) {
        try {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    onSuccess()
                }
                .addOnFailureListener {
                    onFailure(it)
                }
        } catch (e: Exception) {
            onFailure(e)
        }
    }

    override suspend fun signOut(
        onSuccess: () -> Unit,
        onFailure: (e: Exception) -> Unit
    ) {
        try {
            val authStateListener = FirebaseAuth.AuthStateListener { auth ->
                if (auth.currentUser == null) {
                    onSuccess()
                } else {
                    onFailure(Exception("Fail to sign out"))
                }
            }
            auth.addAuthStateListener(authStateListener)
            auth.signOut()

        } catch (e: Exception) {
            onFailure(e)
        }
    }
}