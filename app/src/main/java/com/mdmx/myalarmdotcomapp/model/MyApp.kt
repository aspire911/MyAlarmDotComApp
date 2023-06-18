package com.mdmx.myalarmdotcomapp.model

import android.app.Application
import com.google.gson.Gson
import com.mdmx.myalarmdotcomapp.Constant
import com.mdmx.myalarmdotcomapp.data.garagestate.GarageState
import com.mdmx.myalarmdotcomapp.data.systemdata.SystemData
import com.mdmx.myalarmdotcomapp.data.systemid.AvailableSystemItem
import org.jsoup.Connection
import org.jsoup.Jsoup

class MyApp : Application() {
    companion object {
        lateinit var cookies: Map<String, String>
        private val gson = Gson()
        private fun getJson(url: String): String {
            val ajaxKey = cookies["afg"]
            val response = Jsoup.connect(url)
                .ignoreHttpErrors(true)
                .ignoreContentType(true)
                .method(Connection.Method.GET)
                .header("Accept", "application/vnd.api+json")
                .cookies(cookies)
                .header("AjaxRequestUniqueKey", ajaxKey)
                .referrer("https://www.alarm.com/web/system/home")
                .userAgent(Constant.USER_AGENT)
                .execute()
            return response.body().toString()
        }

        fun getSystemData(): SystemData {
            val systemId =
                gson.fromJson(getJson(Constant.SYSTEMID_URL), AvailableSystemItem::class.java)
            return gson.fromJson(
                getJson(Constant.SYSTEMDATA_URL + systemId.data[0].id),
                SystemData::class.java
            )
        }

        fun getGarageDoorState(garageDoorId: String): Int {
            val garageState = gson.fromJson(
                getJson(Constant.GARAGESTATE_URL + garageDoorId),
                GarageState::class.java
            )
            return garageState.data.attributes.state

        }

    }

}