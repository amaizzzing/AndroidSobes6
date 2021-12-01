package com.amaizzzing.sobes6

import android.app.Application
import com.amaizzzing.sobes6.di.AppComponent
import com.amaizzzing.sobes6.di.DaggerAppComponent

class HealthApp: Application() {
    override fun onCreate() {
        super.onCreate()

        instance = this
        appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }

    companion object {
        lateinit var instance: HealthApp
        lateinit var appComponent: AppComponent
    }
}