package com.mdmx.myalarmdotcomapp.testutil

import org.jsoup.Connection
import org.jsoup.nodes.Document
import java.io.BufferedInputStream
import java.net.URL


class TestResponse(private val key: String, private val value: String) : Connection.Response {

    override fun url(): URL {
        TODO("Not yet implemented")
    }

    override fun url(url: URL?): Connection.Response {
        TODO("Not yet implemented")
    }

    override fun method(): Connection.Method {
        TODO("Not yet implemented")
    }

    override fun method(method: Connection.Method?): Connection.Response {
        TODO("Not yet implemented")
    }

    override fun header(name: String?): String {
        TODO("Not yet implemented")
    }

    override fun header(name: String?, value: String?): Connection.Response {
        TODO("Not yet implemented")
    }

    override fun headers(name: String?): MutableList<String> {
        TODO("Not yet implemented")
    }

    override fun headers(): MutableMap<String, String> {
        TODO("Not yet implemented")
    }

    override fun addHeader(name: String?, value: String?): Connection.Response {
        TODO("Not yet implemented")
    }

    override fun hasHeader(name: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasHeaderWithValue(name: String?, value: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeHeader(name: String?): Connection.Response {
        TODO("Not yet implemented")
    }

    override fun multiHeaders(): MutableMap<String, MutableList<String>> {
        TODO("Not yet implemented")
    }

    override fun cookie(name: String?): String {
        TODO("Not yet implemented")
    }

    override fun cookie(name: String?, value: String?): Connection.Response {
        TODO("Not yet implemented")
    }

    override fun hasCookie(name: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeCookie(name: String?): Connection.Response {
        TODO("Not yet implemented")
    }

    override fun cookies(): MutableMap<String, String> {
        return mutableMapOf(key to value)
    }

    override fun statusCode(): Int {
        TODO("Not yet implemented")
    }

    override fun statusMessage(): String {
        TODO("Not yet implemented")
    }

    override fun charset(): String {
        TODO("Not yet implemented")
    }

    override fun charset(charset: String?): Connection.Response {
        TODO("Not yet implemented")
    }

    override fun contentType(): String {
        TODO("Not yet implemented")
    }

    override fun parse(): Document {
        TODO("Not yet implemented")
    }

    override fun body(): String {
        TODO("Not yet implemented")
    }

    override fun bodyAsBytes(): ByteArray {
        TODO("Not yet implemented")
    }

    override fun bufferUp(): Connection.Response {
        TODO("Not yet implemented")
    }

    override fun bodyStream(): BufferedInputStream {
        TODO("Not yet implemented")
    }

}
