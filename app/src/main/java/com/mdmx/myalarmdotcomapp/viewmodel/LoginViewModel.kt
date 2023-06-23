package com.mdmx.myalarmdotcomapp.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdmx.myalarmdotcomapp.AlarmDotComApplication
import com.mdmx.myalarmdotcomapp.model.apirepository.ApiRepository
import com.mdmx.myalarmdotcomapp.model.sprepository.SpRepository
import com.mdmx.myalarmdotcomapp.util.Constant.ERROR
import com.mdmx.myalarmdotcomapp.util.Constant.ERROR_LOGIN_PASS
import com.mdmx.myalarmdotcomapp.util.Constant.LOGGEDIN
import com.mdmx.myalarmdotcomapp.util.Constant.LOGGEDIN_FIELD
import com.mdmx.myalarmdotcomapp.util.Constant.LOGIN_KEY
import com.mdmx.myalarmdotcomapp.util.Constant.LOGIN_OK
import com.mdmx.myalarmdotcomapp.util.Constant.PASSWORD_KEY
import com.mdmx.myalarmdotcomapp.util.DispatcherProvider
import com.mdmx.myalarmdotcomapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val spRepository: SpRepository,
    private val apiRepository: ApiRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    sealed class LoginEvent {
        class Success(val resultText: String) : LoginEvent()
        class Failure(val errorText: String) : LoginEvent()
        object Loading : LoginEvent()
        object Empty : LoginEvent()
    }

    private val _result = MutableStateFlow<LoginEvent>(LoginEvent.Empty)
    val result: StateFlow<LoginEvent> = _result

    private var _autoLogin = MutableLiveData(true)
    val autoLogin: MutableLiveData<Boolean> = _autoLogin

    fun login(login: String, password: String, autoLogin: Boolean = true) {

        if (login.isBlank() || password.isBlank()) {
            _result.value = LoginEvent.Failure(ERROR_LOGIN_PASS)
            return
        }

        viewModelScope.launch(dispatchers.io) {
            _result.value = LoginEvent.Loading
            when (val loginResponse = apiRepository.login(login = login, password = password)) {
                is Resource.Error<*> -> _result.value =
                    LoginEvent.Failure(ERROR)

                is Resource.Success<*> -> {
                    val loggedIn = loginResponse.data?.cookies()?.get(LOGGEDIN_FIELD)
                    if (loggedIn != null && loggedIn == LOGGEDIN) {
                        AlarmDotComApplication.cookies = loginResponse.data.cookies()
                        if(autoLogin) spRepository.setLoginData(login, password)
                        _result.value = LoginEvent.Success(LOGIN_OK)
                    } else {
                        _result.value = LoginEvent.Failure(ERROR)
                    }
                }
            }
        }
    }

    fun autoLogin() {
        _result.value = LoginEvent.Empty
        if(spRepository.autoLogin()) {
            val loginData = spRepository.getLoginData()
            val login = loginData[LOGIN_KEY]
            val password = loginData[PASSWORD_KEY]
            if(!login.isNullOrBlank() && !password.isNullOrBlank())
                login(login, password)
        }
        else _autoLogin.value = false
    }
}