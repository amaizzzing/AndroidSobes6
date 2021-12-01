package com.amaizzzing.sobes6.services.database.repositories

import com.amaizzzing.sobes6.entities.HealthEntity

interface IHealthRepository {
    suspend fun getAllRecords(): List<Any>

    suspend fun insertHealthEntity(healthEntity: HealthEntity): Result<Unit>?
}