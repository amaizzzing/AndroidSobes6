package com.amaizzzing.sobes6.di

import com.amaizzzing.sobes6.HealthApp
import com.amaizzzing.sobes6.MainActivity
import com.amaizzzing.sobes6.di.modules.ImageModule
import com.amaizzzing.sobes6.di.modules.RepositoryModule
import com.amaizzzing.sobes6.di.modules.ServiceModule
import com.amaizzzing.sobes6.di.modules.ViewModelModule
import com.amaizzzing.sobes6.ui.AddHealthDialogFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RepositoryModule::class,
        ServiceModule::class,
        ViewModelModule::class,
        ImageModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: HealthApp): Builder

        fun build(): AppComponent
    }

    fun inject(mainActivity: MainActivity)

    fun inject(addHealthDialogFragment: AddHealthDialogFragment)
}