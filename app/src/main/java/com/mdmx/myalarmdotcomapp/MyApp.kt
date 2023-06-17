package com.mdmx.myalarmdotcomapp

import android.app.Application
import android.util.Log
import com.google.gson.Gson
import com.mdmx.myalarmdotcomapp.data.garagestate.GarageState
import com.mdmx.myalarmdotcomapp.data.systemdata.SystemData
import com.mdmx.myalarmdotcomapp.data.systemid.AvailableSystemItem
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class MyApp : Application() {
    companion object {
        lateinit var cookies: Map<String, String>
        private val gson = Gson()

        fun login(login: String, password: String): Boolean {
            var flag = false
            try {
                val doc: Document = Jsoup.connect(Constant.LOGIN_URL)
                    .userAgent(Constant.USER_AGENT)
                    .get()
                val formInputs: Elements? = doc.select("input")
                val formData = HashMap<String, String>()
                if (formInputs != null) {
                    for (input in formInputs) {
                        if (input.id().startsWith("__"))
                            formData[input.id()] = input.`val`()
                    }
                }
                formData["IsFromNewSite"] = "1"
                formData["JavaScriptTest"] = "1"
                formData["ctl00\$ContentPlaceHolder1\$loginform\$txtUserName"] = login
                formData["txtPassword"] = password
                val homePage: Connection.Response = Jsoup.connect(Constant.FORMLOGIN_URL)
                    .data(formData)
                    .method(Connection.Method.POST)
                    .userAgent(Constant.USER_AGENT)
                    .execute()

                val loggedIn = homePage.cookies()["adc_e_loggedInAsSubscriber"]
                if (loggedIn != null && loggedIn == "1") {
                    Log.d("MYLOG", "Login successful")
                    cookies = homePage.cookies()
                    flag = true

                } else Log.d("MYLOG", "Login failed")
            } catch (e: IOException) {
                Log.d("MYLOG", e.message.toString())
            }
            return flag
        }


        fun getJson(url: String): String {
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