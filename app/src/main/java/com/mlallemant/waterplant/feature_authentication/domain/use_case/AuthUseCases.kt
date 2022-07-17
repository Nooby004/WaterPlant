package com.mlallemant.waterplant.feature_authentication.domain.use_case

data class AuthUseCases(
    val isUserAuthenticated: IsUserAuthenticatedUseCase,
    val signInWithEmailPassword: SignInWithEmailPasswordUseCase,
    val signOut: SignOutUseCase,
)
