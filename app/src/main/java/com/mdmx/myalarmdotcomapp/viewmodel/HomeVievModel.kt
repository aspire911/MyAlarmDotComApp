package com.mdmx.myalarmdotcomapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdmx.myalarmdotcomapp.AlarmDotComApplication.Companion.logger
import com.mdmx.myalarmdotcomapp.util.DispatcherProvider
import com.mdmx.myalarmdotcomapp.model.MainRepository
import com.mdmx.myalarmdotcomapp.util.Constant.ERROR
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

    fun getSystemData() {
        viewModelScope.launch(dispatchers.io) {
            val systemData = repository.getSystemData()

            if(systemData != null) {
                logger.d(systemData.data.attributes.description)
                _title.postValue(systemData.data.attributes.description)
                if(systemData.data.relationships.garageDoors.data.isNotEmpty()) {
                    val garageDoorId = systemData.data.relationships.garageDoors.data[0].id
                    val garageState = repository.getGarageDoorState(garageDoorId)
                    _state.postValue(garageState)
                }
            } else {
                _title.postValue(ERROR)
            }

        }
    }
}