package com.amaizzzing.sobes6.ui.models

interface IColorStrategy {
    fun getColorBackground(): Int

    companion object {
        fun getColorStrategy(
            top: Int,
            bottom: Int,
            heartbeat: Int
        ): IColorStrategy {
            return when {
                top > 140 || top < 100 || bottom > 100 || bottom < 60 || heartbeat > 80 || heartbeat < 40 ->
                    RedColorStrategy()

                (top in 130..140) || (top in 100..110) || (bottom in 90..100) || (bottom in 60..70) || (heartbeat in 65..80) || (heartbeat in 40..45) ->
                    YellowColorStrategy()

                else -> GreenColorStrategy()
            }
        }
    }
}