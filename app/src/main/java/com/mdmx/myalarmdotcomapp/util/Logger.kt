package com.mdmx.myalarmdotcomapp.util

import android.util.Log
import com.mdmx.myalarmdotcomapp.util.Constant.LOGGER_TAG


class Logger(private val isEnabled: Boolean) {

    fun e(message: String) {
        if(isEnabled) Log.e(LOGGER_TAG, message)
    }

    fun w(message: String) {
        if(isEnabled) Log.w(LOGGER_TAG, message)
    }

    fun v(message: String) {
        if(isEnabled) Log.v(LOGGER_TAG, message)
    }

    fun d(message: String) {
        if(isEnabled) Log.d(LOGGER_TAG, message)
    }
}