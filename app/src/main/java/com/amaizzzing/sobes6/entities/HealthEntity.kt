package com.amaizzzing.sobes6.entities

data class HealthEntity(
    val id: String = "",
    val top: Int,
    val bottom: Int,
    val heartbeat: Int,
    val time: Long
)