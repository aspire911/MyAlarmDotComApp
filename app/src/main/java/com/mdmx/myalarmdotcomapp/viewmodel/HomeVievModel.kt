package com.mdmx.myalarmdotcomapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdmx.myalarmdotcomapp.util.DispatcherProvider
import com.mdmx.myalarmdotcomapp.model.MainRepository
import com.mdmx.myalarmdotcomapp.util.Constant.ERROR
import com.mdmx.myalarmdotcomapp.util.Constant.NO_GARAGE_DOORS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MainRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _title = MutableLiveData("")
    val title: LiveData<String> = _title

    private val _state = MutableLiveData(0)
    val state: LiveData<Int> = _state

    fun updateSystemData() {
        viewModelScope.launch(dispatchers.io) {
            val availableSystemItem = repository.getAvailableSystemItem()
            val systemId = availableSystemItem?.data?.get(0)?.id
            if (systemId != null) {
                val systemData = repository.getSystemData(systemId)
                val text = systemData?.data?.attributes?.description
                val garageDoorId = systemData?.data?.relationships?.garageDoors?.data?.get(0)?.id
                _title.postValue(text ?: ERROR)
                if (garageDoorId != null) {
                    val garageData = repository.getGarageDoorData(garageDoorId)
                    val garageState = garageData?.data?.attributes?.state
                    if (garageState != null) {
                        _state.postValue(garageState)
                    } else _state.postValue(NO_GARAGE_DOORS)
                } else _state.postValue(NO_GARAGE_DOORS)
            } else _title.postValue(ERROR)
        }
    }
}