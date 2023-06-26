package com.mdmx.myalarmdotcomapp.model.localpersistentrepository


import android.content.SharedPreferences
import com.mdmx.myalarmdotcomapp.util.Constant.AUTOLOGIN_KEY
import com.mdmx.myalarmdotcomapp.util.Constant.EMPTY_STRING
import com.mdmx.myalarmdotcomapp.util.Constant.LOGIN_KEY
import com.mdmx.myalarmdotcomapp.util.Constant.PASSWORD_KEY
import javax.inject.Inject

class LocalPersistentRepository @Inject constructor(
    private val securePreferences: SharedPreferences
): LocalPersistentDataSource {

    override fun autoLogin(): Boolean {
        return securePreferences.getBoolean(AUTOLOGIN_KEY, false)
    }

    override fun setLoginData(login: String, password: String, autoLogin: Boolean) {
        with(securePreferences.edit()) {
            putBoolean(AUTOLOGIN_KEY, autoLogin)
            putString(LOGIN_KEY, login)
            putString(PASSWORD_KEY, password)
            commit()
        }
    }

    override fun getLoginData(): Map<String, String?> {
        val login = securePreferences.getString(LOGIN_KEY, EMPTY_STRING)
        val password = securePreferences.getString(PASSWORD_KEY, EMPTY_STRING)
        return mapOf(LOGIN_KEY to login, PASSWORD_KEY to password)
    }

    override fun clearLoginData() {
        securePreferences.edit().clear().apply()
    }

}