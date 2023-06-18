package com.mdmx.myalarmdotcomapp.model

import com.mdmx.myalarmdotcomapp.util.Resource
import org.jsoup.Connection

interface MainRepository {
    suspend fun login(login: String, password: String): Resource<Connection.Response>
    }
