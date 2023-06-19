package com.mdmx.myalarmdotcomapp.model

import com.google.gson.Gson
import com.mdmx.myalarmdotcomapp.AlarmDotComApplication
import com.mdmx.myalarmdotcomapp.data.garagestate.GarageState
import com.mdmx.myalarmdotcomapp.data.systemdata.SystemData
import com.mdmx.myalarmdotcomapp.data.systemid.AvailableSystemItem
import com.mdmx.myalarmdotcomapp.util.Constant
import com.mdmx.myalarmdotcomapp.util.Constant.ACCEPT
import com.mdmx.myalarmdotcomapp.util.Constant.AJAX_KEY_FIELD
import com.mdmx.myalarmdotcomapp.util.Constant.AJAX_REQUEST
import com.mdmx.myalarmdotcomapp.util.Constant.ERROR
import com.mdmx.myalarmdotcomapp.util.Constant.HOME_URL
import com.mdmx.myalarmdotcomapp.util.Constant.INPUT
import com.mdmx.myalarmdotcomapp.util.Constant.ISFORMNEWSITE
import com.mdmx.myalarmdotcomapp.util.Constant.LOGIN
import com.mdmx.myalarmdotcomapp.util.Constant.LOGIN_URL
import com.mdmx.myalarmdotcomapp.util.Constant.PASSWORD
import com.mdmx.myalarmdotcomapp.util.Constant.PREFIX
import com.mdmx.myalarmdotcomapp.util.Constant.SYSTEMID_URL
import com.mdmx.myalarmdotcomapp.util.Constant.TRUE_1
import com.mdmx.myalarmdotcomapp.util.Constant.TYPE
import com.mdmx.myalarmdotcomapp.util.Constant.USER_AGENT
import com.mdmx.myalarmdotcomapp.util.Resource
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class DefaultMainRepository : MainRepository {

    private val gson = Gson()
    override suspend fun login(login: String, password: String): Resource<Connection.Response> {
        val response: Connection.Response
        try {
            val doc: Document = Jsoup.connect(LOGIN_URL)
                .userAgent(USER_AGENT)
                .get()
            val formInputs: Elements? = doc.select(INPUT)
            val formData = HashMap<String, String>()
            if (formInputs != null) {
                for (input in formInputs) {
                    if (input.id().startsWith(PREFIX))
                        formData[input.id()] = input.`val`()
                }
            }
            formData[ISFORMNEWSITE] = TRUE_1
            formData[LOGIN] = login
            formData[PASSWORD] = password
            response = Jsoup.connect(Constant.FORMLOGIN_URL)
                .data(formData)
                .method(Connection.Method.POST)
                .userAgent(USER_AGENT)
                .execute()

        } catch (e: IOException) {
            return Resource.Error(e.message ?: ERROR)
        }
        return Resource.Success(response)
    }

    override suspend fun getJson(url: String): String {
        val response: Connection.Response
        try {
            val ajaxKey = AlarmDotComApplication.cookies[AJAX_KEY_FIELD]
            response = Jsoup.connect(url)
                .ignoreHttpErrors(true)
                .ignoreContentType(true)
                .method(Connection.Method.GET)
                .header(ACCEPT, TYPE)
                .cookies(AlarmDotComApplication.cookies)
                .header(AJAX_REQUEST, ajaxKey)
                .referrer(HOME_URL)
                .userAgent(USER_AGENT)
                .execute()
        } catch (e: IOException) {
            return ""
        }
        return response.body().toString()
    }

    override suspend fun getSystemData(): SystemData? {
        var response = getJson(SYSTEMID_URL)
        if(response.isNotEmpty()) {
            val systemId = gson.fromJson(response, AvailableSystemItem::class.java)
            response = getJson(Constant.SYSTEMDATA_URL + systemId.data[0].id)
            if(response.isNotEmpty()) {
                return gson.fromJson(response, SystemData::class.java)
            }
        }
        return null
    }

    override suspend fun getGarageDoorState(garageDoorId: String): Int {
        val response = getJson(Constant.GARAGESTATE_URL + garageDoorId)
        if(response.isNotEmpty()) {
            val garageState = gson.fromJson(response, GarageState::class.java)
            return garageState.data.attributes.state
        }
        return 0
    }
}
