package com.amaizzzing.sobes6.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amaizzzing.sobes6.services.database.repositories.IHealthRepository
import com.amaizzzing.sobes6.ui.BaseState
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val healthRepository: IHealthRepository
): ViewModel() {
    private val _data = MutableLiveData<BaseState?>()
    val data: LiveData<BaseState?> = _data
    fun setData(newData: BaseState) {
        _data.value = newData
    }

    fun getAllRecords() = viewModelScope.launch {
        setData(BaseState.Loading)

        val list = healthRepository.getAllRecords()

        withContext(viewModelScope.coroutineContext) {
            setData(BaseState.Success(list))
        }
    }
}