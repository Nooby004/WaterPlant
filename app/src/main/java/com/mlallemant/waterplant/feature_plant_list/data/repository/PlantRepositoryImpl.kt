package com.mlallemant.waterplant.feature_plant_list.data.repository

import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.mlallemant.waterplant.feature_plant_list.domain.model.Plant
import com.mlallemant.waterplant.feature_plant_list.domain.model.WaterPlant
import com.mlallemant.waterplant.feature_plant_list.domain.repository.PlantRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber

class PlantRepositoryImpl(
    private val rootRef: DatabaseReference,
    private val auth: FirebaseAuth
) : PlantRepository {

    private val userId = auth.currentUser?.uid!!

    companion object {
        const val USERS_REF = "users"
        const val PLANTS_REF = "plants"
    }

    override fun getPlants(): Flow<List<Plant>> = callbackFlow {
        val successListener = OnSuccessListener<DataSnapshot> { data ->
            val plants: MutableList<Plant> = mutableListOf()
            run {
                repeat(data.children.count()) {
                    data.getValue(Plant::class.java)?.let { it1 -> plants.add(it1) }
                }
            }
            this@callbackFlow.trySendBlocking(plants)
        }

        val task = rootRef.child(USERS_REF).child(userId).get()
        task.addOnSuccessListener(successListener)

        awaitClose {
            task.isComplete
        }
    }

    override suspend fun getPlant(plantId: String): Flow<Plant> = callbackFlow {

        val successListener = OnSuccessListener<DataSnapshot> { data ->
            this@callbackFlow.trySendBlocking(data.getValue(Plant::class.java)!!)
        }

        val task = rootRef.child(USERS_REF).child(userId).child(PLANTS_REF).child(plantId).get()
        task.addOnSuccessListener(successListener)
        
        awaitClose {
            task.isComplete
        }
    }


    override suspend fun addPlant(plant: Plant) {
        rootRef.child(USERS_REF).child(userId).child(PLANTS_REF).child(plant.id)
            .setValue(plant)
            .addOnSuccessListener {
                Timber.d("Success")
            }
            .addOnFailureListener { e ->
                run {
                    Timber.e(e)
                }
            }

    }

    override suspend fun addWaterPlant(plantId: String, waterPlant: WaterPlant) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePlant(plantId: String) {
        TODO("Not yet implemented")
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