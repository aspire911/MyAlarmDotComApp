package com.mdmx.myalarmdotcomapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdmx.currencyconvertor.util.DispatcherProvider
import com.mdmx.myalarmdotcomapp.model.MainRepository
import com.mdmx.myalarmdotcomapp.model.MyApp
import com.mdmx.myalarmdotcomapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
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

    fun login(login: String, password: String) {

        if (login.isBlank() || password.isBlank()) {
            _result.value = LoginEvent.Failure("Input Login or Password")
            return
        }


        viewModelScope.launch(dispatchers.io) {
            _result.value = LoginEvent.Loading
            when (val loginResponse = repository.login(login, password)) {
                is Resource.Error<*> -> _result.value =
                    LoginEvent.Failure(loginResponse.massage!!)

                is Resource.Success<*> -> {
                    val loggedIn = loginResponse.data!!.cookies()["adc_e_loggedInAsSubscriber"]
                    if (loggedIn == "1") {
                        Log.d("MYLOG", "Login successful")
                        MyApp.cookies = loginResponse.data.cookies()
                        _result.value = LoginEvent.Success("LOGIN OK")
                    } else {
                        _result.value = LoginEvent.Failure("ERROR!")
                    }

                }
            }
        }
    }
}