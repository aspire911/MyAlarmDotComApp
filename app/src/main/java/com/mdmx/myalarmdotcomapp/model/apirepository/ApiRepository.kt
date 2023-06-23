package com.mdmx.myalarmdotcomapp.model.apirepository

import com.mdmx.myalarmdotcomapp.data.garagestate.GarageState
import com.mdmx.myalarmdotcomapp.data.systemdata.SystemData
import com.mdmx.myalarmdotcomapp.data.systemid.AvailableSystemItem
import com.mdmx.myalarmdotcomapp.util.Resource
import org.jsoup.Connection

interface ApiRepository {
    suspend fun login(login: String, password: String): Resource<Connection.Response>

    suspend fun getJson(url: String): String

    suspend fun getAvailableSystemItem(): AvailableSystemItem?

    suspend fun getSystemData(systemId: String): SystemData?

    suspend fun getGarageDoorData(garageDoorId: String): GarageState?
}
