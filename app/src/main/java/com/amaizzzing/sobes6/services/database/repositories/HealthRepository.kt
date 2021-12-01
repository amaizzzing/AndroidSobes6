package com.amaizzzing.sobes6.services.database.repositories

import com.amaizzzing.sobes6.entities.HealthEntity
import com.amaizzzing.sobes6.services.database.IFireDatabase
import com.amaizzzing.sobes6.ui.models.HealthModel
import com.amaizzzing.sobes6.utils.DateUtils

class HealthRepository(
    private val fireDatabase: IFireDatabase
): IHealthRepository {
    override suspend fun getAllRecords(): List<Any> {
        val res = fireDatabase.getAllRecords()
        return if (res?.isSuccess == true) {
            val list = res.getOrNull()
                ?.map {
                    HealthEntity(
                        id = it.id,
                        top = it.getLong("top")?.toInt() ?: 0,
                        bottom = it.getLong("bottom")?.toInt() ?: 0,
                        heartbeat = it.getLong("heartbeat")?.toInt() ?: 0,
                        time = it.getLong("time")?: 0
                    )
                }

            val dateSet = list?.sortedByDescending { it.time }?.map { DateUtils.atStartOfDay(it.time) to DateUtils.atEndOfDay(it.time)}?.toSet()
            val resultList: MutableList<Any> = mutableListOf()
            dateSet?.forEach { date ->
                resultList.add(DateUtils.millisToDateString(date.first))
                resultList.addAll(
                    list
                        .filter { it.time >= date.first && it.time <= date.second }
                        .sortedByDescending { it.time }
                        .map { HealthModel(it) }
                )
            }

            resultList
        } else {
            listOf()
        }
    }

    override suspend fun insertHealthEntity(healthEntity: HealthEntity): Result<Unit>? {
        return fireDatabase.insertHealthEntity(healthEntity)
    }
}