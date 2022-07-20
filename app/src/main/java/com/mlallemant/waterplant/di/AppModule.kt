package com.mlallemant.waterplant.di

import android.app.Application
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mlallemant.waterplant.feature_authentication.data.repository.AuthRepositoryImpl
import com.mlallemant.waterplant.feature_authentication.domain.repository.AuthRepository
import com.mlallemant.waterplant.feature_authentication.domain.use_case.*
import com.mlallemant.waterplant.feature_plant_list.data.data_source.PlantDatabase
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
    fun providePlantDatabase(app: Application): PlantDatabase {
        return Room.databaseBuilder(
            app,
            PlantDatabase::class.java,
            PlantDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providePlantRepository(db: PlantDatabase): PlantRepository {
        return PlantRepositoryImpl(db.plantDao)
    }

    @Provides
    @Singleton
    fun providePlantUseCases(repository: PlantRepository): PlantUseCases {
        return PlantUseCases(
            getPlants = GetPlantsUseCase(repository),
            addPlant = AddPlantUseCase(repository),
            getPlant = GetPlantUseCase(repository),
            addWaterToPlant = AddWaterToPlantUseCase(repository),
            deletePlant = DeletePlantUseCase(repository),
            getNextWatering = GetNextWateringUseCase(repository)
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

}