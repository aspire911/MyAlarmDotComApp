package com.mdmx.myalarmdotcomapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdmx.myalarmdotcomapp.AlarmDotComApplication
import com.mdmx.myalarmdotcomapp.model.alarmdotcomremoterepository.AlarmDotComRemoteDataSource
import com.mdmx.myalarmdotcomapp.model.localpersistentrepository.LocalPersistentDataSource
import com.mdmx.myalarmdotcomapp.util.Constant.ERROR
import com.mdmx.myalarmdotcomapp.util.Constant.NO_GARAGE_DOORS
import com.mdmx.myalarmdotcomapp.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localPersistentRepository: LocalPersistentDataSource,
    private val alarmDotComRemoteRepository: AlarmDotComRemoteDataSource,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    init {
        updateSystemData()
    }

    fun updateSystemData() {
        viewModelScope.launch(dispatchers.io) {
            _state.value = HomeState(isLoading = true)
            var text: String? = null
            var garageState: Int? = null
            val availableSystemItem = alarmDotComRemoteRepository.getAvailableSystemItem()
            val systemId = availableSystemItem?.data?.get(0)?.id
            if (systemId != null) {
                val systemData = alarmDotComRemoteRepository.getSystemData(systemId)
                text = systemData?.data?.attributes?.description
                val garageDoorId = systemData?.data?.relationships?.garageDoors?.data?.get(0)?.id
                if (garageDoorId != null) {
                    val garageData = alarmDotComRemoteRepository.getGarageDoorData(garageDoorId)
                    garageState = garageData?.data?.attributes?.state
                }
            }
            _state.value = HomeState(
                isLoading = false,
                systemTitle = text ?: ERROR,
                garageState = garageState ?: NO_GARAGE_DOORS
            )
        }
    }

    fun logOut() {
        localPersistentRepository.clearLoginData()
        AlarmDotComApplication.cookies = mapOf()
        _state.value = HomeState(isLogout = true)
    }
}