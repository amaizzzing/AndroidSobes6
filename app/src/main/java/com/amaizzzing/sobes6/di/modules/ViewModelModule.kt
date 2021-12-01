package com.amaizzzing.sobes6.di.modules

import androidx.lifecycle.ViewModel
import com.amaizzzing.sobes6.di.ViewModelKey
import com.amaizzzing.sobes6.ui.viewmodels.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(topRatedViewModel: MainViewModel): ViewModel
}