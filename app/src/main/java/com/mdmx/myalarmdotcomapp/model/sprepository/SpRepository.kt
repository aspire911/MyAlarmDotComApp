package com.mdmx.myalarmdotcomapp.model.sprepository

interface SpRepository {

    fun setLoginData(login: String, password: String, autoLogin: Boolean = true)

    fun getLoginData(): Map<String, String?>

    fun clearLoginData()

    fun autoLogin(): Boolean

}