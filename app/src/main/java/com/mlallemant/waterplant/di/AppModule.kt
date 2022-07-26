package com.mlallemant.waterplant.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.mlallemant.waterplant.feature_authentication.data.repository.AuthRepositoryImpl
import com.mlallemant.waterplant.feature_authentication.domain.repository.AuthRepository
import com.mlallemant.waterplant.feature_authentication.domain.use_case.*
import com.mlallemant.waterplant.feature_plant_list.data.repository.PlantRepositoryImpl
import com.mlallemant.waterplant.feature_plant_list.domain.repository.PlantRepository
import com.mlallemant.waterplant.feature_plant_list.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providePlantUseCases(repository: PlantRepository): PlantUseCases {
        return PlantUseCases(
            getPlants = GetPlantsUseCase(repository),
            savePlant = AddPlantUseCase(repository),
            getPlant = GetPlantUseCase(repository),
            addWaterToPlant = AddWaterToPlantUseCase(repository),
            deletePlant = DeletePlantUseCase(repository),
        )
    }


    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    fun provideAuthRepository(
        auth: FirebaseAuth
    ): AuthRepository = AuthRepositoryImpl(auth)

    @Singleton
    @Provides
    fun provideUseCases(
        repository: AuthRepository
    ) = AuthUseCases(
        isUserAuthenticated = IsUserAuthenticatedUseCase(repository),
        signInWithEmailPassword = SignInWithEmailPasswordUseCase(repository),
        signUpWithEmailPassword = SignUpWithEmailPasswordUseCase(repository),
        signOut = SignOutUseCase(repository)
    )

    @Provides
    fun provideFirebaseDatabase() =
        Firebase.database("https://waterplant-90f7f-default-rtdb.europe-west1.firebasedatabase.app").reference


    @Provides
    @Singleton
    fun providePlantRepository(
        auth: FirebaseAuth,
        databaseRootRef: DatabaseReference,
        storageRootRef: StorageReference
    ): PlantRepository {
        return PlantRepositoryImpl(auth, databaseRootRef, storageRootRef)
    }

    @Provides
    fun provideFirebaseStorage() = Firebase.storage.reference
}