package com.mdmx.myalarmdotcomapp.model.apirepository

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.mdmx.myalarmdotcomapp.AlarmDotComApplication
import com.mdmx.myalarmdotcomapp.AlarmDotComApplication.Companion.logger
import com.mdmx.myalarmdotcomapp.data.garagestate.GarageState
import com.mdmx.myalarmdotcomapp.data.systemdata.SystemData
import com.mdmx.myalarmdotcomapp.data.systemid.AvailableSystemItem
import com.mdmx.myalarmdotcomapp.util.Constant
import com.mdmx.myalarmdotcomapp.util.Constant.ACCEPT
import com.mdmx.myalarmdotcomapp.util.Constant.AJAX_KEY_FIELD
import com.mdmx.myalarmdotcomapp.util.Constant.AJAX_REQUEST
import com.mdmx.myalarmdotcomapp.util.Constant.EMPTY_STRING
import com.mdmx.myalarmdotcomapp.util.Constant.ERROR
import com.mdmx.myalarmdotcomapp.util.Constant.FORMLOGIN_URL
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
import javax.inject.Inject

class DefaultApiRepository @Inject constructor(

    private val gson: Gson

    ) : ApiRepository {


    override suspend fun login(login: String, password: String): Resource<Connection.Response> {
        var response: Connection.Response? = null
        try {
            // Load the first page in order to pull the ASP states/keys so our login request looks legit
            logger.d("Loading initial page from $LOGIN_URL")
            val doc: Document? = Jsoup.connect(LOGIN_URL)
                .timeout(10000)
                .userAgent(USER_AGENT)
                .get()
            if(doc != null) {
                logger.d("Parsing HTML response")
                // We need all the hidden ASP.NET state/event values.
                val formInputs: Elements? = doc.select(INPUT)
                val formData = HashMap<String, String>()
                if (formInputs != null) {
                    // Grab everything that starts with double underscores just to make sure we get everything
                    for (input in formInputs) {
                        if (input.id().startsWith(PREFIX))
                            formData[input.id()] = input.`val`()
                    }
                }
                formData[ISFORMNEWSITE] = TRUE_1 // Not sure what this does exactly, but it seems necessary to include it
                formData[LOGIN] = login // Username
                formData[PASSWORD] = password // Password
                // Submit the login form
                logger.d("Submitting login form $FORMLOGIN_URL")
                response = Jsoup.connect(FORMLOGIN_URL)
                    .timeout(10000)
                    .data(formData)
                    .method(Connection.Method.POST)
                    .userAgent(USER_AGENT)
                    .execute()
            }
        } catch (e: IOException) {
            return Resource.Error(e.message ?: ERROR)
        }
        return if(response != null && response.statusCode() == 200) Resource.Success(response)
        else Resource.Error(ERROR)
    }

    override suspend fun getJson(url: String): String {
        val response: Connection.Response?
        try {
            logger.d("Requesting json from url $url")
            val ajaxKey = AlarmDotComApplication.cookies[AJAX_KEY_FIELD]
            if(ajaxKey != null) {
                response = Jsoup.connect(url)
                    .timeout(10000)
                    .ignoreHttpErrors(true)
                    .ignoreContentType(true)
                    .method(Connection.Method.GET)
                    .header(ACCEPT, TYPE)
                    .cookies(AlarmDotComApplication.cookies)
                    .header(AJAX_REQUEST, ajaxKey)
                    .referrer(HOME_URL)
                    .userAgent(USER_AGENT)
                    .execute()
                if(response != null && response.statusCode() == 200) {
                    logger.d("Got ${response.body()}")
                    return response.body().toString()
                }
            }
        } catch (e: IOException) {
            logger.d("Request failed ${e.message}")
            return EMPTY_STRING
        }
        logger.d("Request failed")
        return EMPTY_STRING
    }

    override suspend fun getAvailableSystemItem(): AvailableSystemItem? {
        val availableSystemItem: AvailableSystemItem?
        val response = getJson(SYSTEMID_URL)
        if(response.isNotEmpty()) {
            try {
                availableSystemItem = gson.fromJson(response, AvailableSystemItem::class.java)
            } catch (e: JsonParseException) {
                logger.d("Parse json AvailableSystemItem failed ${e.message}")
                return null
            }
            return availableSystemItem
        }
        return null
    }

    override suspend fun getSystemData(systemId: String): SystemData? {
        val systemData: SystemData?
        val response = getJson(Constant.SYSTEMDATA_URL + systemId)
        if(response.isNotEmpty()) {
            try {
                systemData = gson.fromJson(response, SystemData::class.java)
            } catch (e: JsonParseException) {
                logger.d("Parse json SystemData failed ${e.message}")
                return null
            }
            return systemData
        }
        return null
    }

    override suspend fun getGarageDoorData(garageDoorId: String): GarageState? {
        val garageState : GarageState?
        val response = getJson(Constant.GARAGESTATE_URL + garageDoorId)
        if(response.isNotEmpty()) {
            try {
                garageState = gson.fromJson(response, GarageState::class.java)
            } catch (e: JsonParseException) {
                logger.d("Parse json SystemData failed ${e.message}")
                return null
            }
            return garageState
        }
        return null
    }
}
