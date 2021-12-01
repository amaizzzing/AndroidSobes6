package com.amaizzzing.sobes6.di.modules

import android.view.ViewGroup
import com.amaizzzing.sobes6.services.image.GlideBackgroundLoader
import com.amaizzzing.sobes6.services.image.IImageLoader
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ImageModule {
    @Singleton
    @Provides
    fun backgroundImageLoader(): IImageLoader<ViewGroup> =
        GlideBackgroundLoader()
}