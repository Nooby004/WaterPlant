package com.mlallemant.waterplant.feature_plant_list.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageReference
import com.mlallemant.waterplant.feature_plant_list.domain.model.Plant
import com.mlallemant.waterplant.feature_plant_list.domain.model.WaterPlant
import com.mlallemant.waterplant.feature_plant_list.domain.repository.PlantRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.io.File

class PlantRepositoryImpl(
    auth: FirebaseAuth,
    private val databaseRootRef: DatabaseReference,
    private val storageRootRef: StorageReference
) : PlantRepository {

    private val userId = auth.currentUser?.uid!!

    companion object {
        const val USERS_REF = "users"
        const val PLANTS_REF = "plants"
        const val WATER_PLANT_REF = "waterPlants"
        const val MAIN_PICTURE = "main_picture.jpg"
    }

    override fun getPlants(): Flow<List<Plant>> = callbackFlow {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val plants: MutableList<Plant> = mutableListOf()
                run {
                    snapshot.children.forEach {
                        it.getValue(Plant::class.java)?.let { plant ->
                            plants.add(plant)
                        }
                    }
                }
                trySend(plants)
            }

            override fun onCancelled(error: DatabaseError) {
                Timber.d(error.message)
            }
        }

        val task = databaseRootRef.child(USERS_REF).child(userId).child(PLANTS_REF)
        task.addValueEventListener(valueEventListener)

        awaitClose {
            task.removeEventListener(valueEventListener)
        }
    }

    override suspend fun getPlant(plantId: String): Plant? {

        return try {
            val snapshot =
                databaseRootRef.child(USERS_REF).child(userId).child(PLANTS_REF).child(plantId)
                    .get()
                    .await()
            snapshot.getValue(Plant::class.java)

        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }


    override suspend fun addPlant(plant: Plant) {

        var plantToSave: Plant = plant

        try {

            if (plant.picturePath.isNotEmpty()) {
                val file = File(plant.picturePath)

                // get storage ref
                val ref = storageRootRef.child(USERS_REF).child(userId).child(plant.id)
                    .child(MAIN_PICTURE)

                // upload file
                ref.putBytes(file.readBytes()).await()

                // get the download url
                val downloadPath = ref.downloadUrl.await()

                // change the picturePath with the url
                downloadPath?.let {
                    plantToSave = plantToSave.copy(
                        picturePath = it.toString()
                    )
                }

                // delete file
                file.delete()
            }

            // save plant
            databaseRootRef.child(USERS_REF).child(userId).child(PLANTS_REF).child(plant.id)
                .setValue(plantToSave).await()

        } catch (e: Exception) {
            Timber.e(e)
        }

    }

    override suspend fun addWaterPlant(plantId: String, waterPlant: WaterPlant) {

        var waterPlantToSave: WaterPlant = waterPlant

        try {

            if (waterPlant.picturePath.isNotEmpty()) {
                val file = File(waterPlant.picturePath)

                // get storage ref
                val ref = storageRootRef.child(USERS_REF).child(userId).child(plantId)
                    .child(waterPlant.timestamp.toString())

                // upload file
                ref.putBytes(file.readBytes()).await()

                // get the download url
                val downloadPath = ref.downloadUrl.await()

                // change the picturePath with the url
                downloadPath?.let {
                    waterPlantToSave = waterPlantToSave.copy(
                        picturePath = it.toString()
                    )
                }

                // delete file
                file.delete()
            }

            // Save water plant
            databaseRootRef.child(USERS_REF).child(userId).child(PLANTS_REF).child(plantId)
                .child(WATER_PLANT_REF).push()
                .setValue(waterPlantToSave).await()

        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override suspend fun deletePlant(plantId: String) {

        try {
            // remove plant from database
            databaseRootRef.child(USERS_REF).child(userId).child(PLANTS_REF).child(plantId)
                .removeValue().await()

            // remove all picture
            storageRootRef.child(USERS_REF).child(userId).child(plantId).listAll().await().let {
                it.items.forEach { item ->
                    item.delete().await()
                }
            }

        } catch (e: Exception) {
            Timber.e(e)
        }
    }


    /*  override fun getPlants(): Flow<List<Plant>> {
        return dao.getPlants()
    }

    override suspend fun getPlantWithWaterPlants(id: Int): PlantWithWaterPlants {
        return dao.getPlantById(id)
    }

    override suspend fun addPlant(plant: Plant) {
        dao.addPlant(plant)
    }

    override suspend fun addWaterPlant(waterPlant: WaterPlant) {
        dao.addWaterPlant(waterPlant)
    }

    override suspend fun deletePlant(plant: Plant) {
        plant.id?.let {
            val picturePathList = dao.getAllWaterPlantPathPicture(it)
            picturePathList.forEach { picturePath ->
                File(picturePath).delete()
            }

            dao.deleteAllWaterPlant(plantId = it)
        }
        File(plant.picturePath).delete()
        dao.deletePlant(plant)
    }*/
}