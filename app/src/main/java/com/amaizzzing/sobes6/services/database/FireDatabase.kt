package com.amaizzzing.sobes6.services.database

import com.amaizzzing.sobes6.entities.HealthEntity
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

const val DB_COLLECTION_NAME = "health"

class FireDatabase(
    private val db: FirebaseFirestore
): IFireDatabase {
    private val populateList = listOf(
        HealthEntity(
            top = 115,
            bottom = 79,
            heartbeat = 50,
            time = 1629892229000
        ),
        HealthEntity(
            top = 136,
            bottom = 75,
            heartbeat = 65,
            time = 1632543420000
        ),
        HealthEntity(
            top = 110,
            bottom = 60,
            heartbeat = 55,
            time = 1633092840000
        ),
        HealthEntity(
            top = 99,
            bottom = 60,
            heartbeat = 55,
            time = 1633105920000
        )
    )

    override suspend fun getAllRecords(): Result<List<DocumentSnapshot>>? {
        var res:Result<List<DocumentSnapshot>>? = getDbEntities()

        if (res?.isSuccess == true) {
            res.getOrNull()?.let {
                if (it.isEmpty()) {
                    populateList.forEach { healthEntity ->
                        insertHealthEntity(healthEntity)
                    }
                    res = getDbEntities()
                }
            }
        }

        return res
    }

    override suspend fun insertHealthEntity(healthEntity: HealthEntity): Result<Unit>? {
        var res:Result<Unit>? = null

        db.collection(DB_COLLECTION_NAME)
            .add(healthEntity)
            .addOnSuccessListener {
                res = Result.success(Unit)
            }
            .addOnFailureListener {
                res = Result.failure(it)
            }
            .await()

        return res
    }

    private suspend fun getDbEntities(): Result<List<DocumentSnapshot>>? {
        var res:Result<List<DocumentSnapshot>>? = null
        db.collection(DB_COLLECTION_NAME)
            .get()
            .addOnSuccessListener { result ->
                res = Result.success(result.documents)
            }
            .addOnFailureListener {
                res = Result.failure(it)
            }
            .await()

        return res
    }
}