package com.mdmx.myalarmdotcomapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdmx.myalarmdotcomapp.AlarmDotComApplication
import com.mdmx.myalarmdotcomapp.model.apirepository.ApiRepository
import com.mdmx.myalarmdotcomapp.model.sprepository.SpRepository
import com.mdmx.myalarmdotcomapp.util.Constant.EMPTY_STRING
import com.mdmx.myalarmdotcomapp.util.Constant.ERROR
import com.mdmx.myalarmdotcomapp.util.Constant.NO_GARAGE_DOORS
import com.mdmx.myalarmdotcomapp.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val spRepository: SpRepository,
    private val apiRepository: ApiRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {


    private val _title = MutableLiveData(EMPTY_STRING)
    val title: LiveData<String> = _title

    private val _garageDoorState = MutableLiveData(0)
    val garageDoorState: LiveData<Int> = _garageDoorState

    private val _logOut = MutableLiveData(false)
    val logOut: LiveData<Boolean> = _logOut

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()


    fun updateSystemData() {
        _logOut.value = false

        viewModelScope.launch(dispatchers.io) {
            _isRefreshing.value = true
            val availableSystemItem = apiRepository.getAvailableSystemItem()
            val systemId = availableSystemItem?.data?.get(0)?.id
            if (systemId != null) {
                val systemData = apiRepository.getSystemData(systemId)
                val text = systemData?.data?.attributes?.description
                val garageDoorId = systemData?.data?.relationships?.garageDoors?.data?.get(0)?.id
                _title.postValue(text ?: ERROR)
                if (garageDoorId != null) {
                    val garageData = apiRepository.getGarageDoorData(garageDoorId)
                    val garageState = garageData?.data?.attributes?.state
                    if (garageState != null) {
                        _garageDoorState.postValue(garageState)
                    } else _garageDoorState.postValue(NO_GARAGE_DOORS)
                } else _garageDoorState.postValue(NO_GARAGE_DOORS)
            } else _title.postValue(ERROR)
            _isRefreshing.value = false
        }
    }
    fun logOut() {
        spRepository.clearLoginData()
        AlarmDotComApplication.cookies = mapOf()
        _logOut.value = true
    }
}