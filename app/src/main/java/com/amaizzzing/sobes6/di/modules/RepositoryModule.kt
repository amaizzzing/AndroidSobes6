package com.amaizzzing.sobes6.di.modules

import com.amaizzzing.sobes6.services.database.IFireDatabase
import com.amaizzzing.sobes6.services.database.repositories.HealthRepository
import com.amaizzzing.sobes6.services.database.repositories.IHealthRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun healthRepository(fireDatabase: IFireDatabase): IHealthRepository = HealthRepository(fireDatabase)
}