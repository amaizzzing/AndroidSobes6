package com.amaizzzing.sobes6.ui.models

import com.amaizzzing.sobes6.entities.HealthEntity
import com.amaizzzing.sobes6.utils.DateUtils

data class HealthModel(
    val top: String,
    val bottom: String,
    val heartbeat: String,
    val time: String,
    val colorStrategy: IColorStrategy = GreenColorStrategy()
) {
    constructor(healthEntity: HealthEntity): this(
        top = healthEntity.top.toString(),
        bottom = healthEntity.bottom.toString(),
        heartbeat = healthEntity.heartbeat.toString(),
        time = DateUtils.millisToTimeString(healthEntity.time),
        colorStrategy = IColorStrategy.getColorStrategy(healthEntity.top, healthEntity.bottom, healthEntity.heartbeat)
    )
}