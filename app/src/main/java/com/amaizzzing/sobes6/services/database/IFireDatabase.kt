package com.amaizzzing.sobes6.services.database

import com.amaizzzing.sobes6.entities.HealthEntity
import com.google.firebase.firestore.DocumentSnapshot

interface IFireDatabase {
    suspend fun getAllRecords(): Result<List<DocumentSnapshot>>?

    suspend fun insertHealthEntity(healthEntity: HealthEntity): Result<Unit>?
}