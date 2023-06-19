package com.mdmx.myalarmdotcomapp.model

import com.mdmx.myalarmdotcomapp.data.systemdata.SystemData
import com.mdmx.myalarmdotcomapp.util.Resource
import org.jsoup.Connection

interface MainRepository {
    suspend fun login(login: String, password: String): Resource<Connection.Response>

    suspend fun getJson(url: String): String

    suspend fun getSystemData(): SystemData?

    suspend fun getGarageDoorState(garageDoorId: String): Int
}
