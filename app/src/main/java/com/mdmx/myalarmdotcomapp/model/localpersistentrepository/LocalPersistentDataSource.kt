package com.mdmx.myalarmdotcomapp.model.localpersistentrepository

interface LocalPersistentDataSource {

    fun setLoginData(login: String, password: String, autoLogin: Boolean = true)

    fun getLoginData(): Map<String, String?>

    fun clearLoginData()

    fun autoLogin(): Boolean

}