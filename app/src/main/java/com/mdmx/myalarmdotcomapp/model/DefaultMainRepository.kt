package com.mdmx.myalarmdotcomapp.model

import android.util.Log
import com.mdmx.myalarmdotcomapp.Constant
import com.mdmx.myalarmdotcomapp.util.Resource
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

class DefaultMainRepository() : MainRepository {

    override suspend fun login(login: String, password: String): Resource<Connection.Response> {
        Log.d("MYLOG", login)
        val response: Connection.Response
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
            response = Jsoup.connect(Constant.FORMLOGIN_URL)
                .data(formData)
                .method(Connection.Method.POST)
                .userAgent(Constant.USER_AGENT)
                .execute()

        } catch (e: IOException) {
            return Resource.Error(e.message ?: "ERROR")
        }
        return Resource.Success(response)
    }
}
