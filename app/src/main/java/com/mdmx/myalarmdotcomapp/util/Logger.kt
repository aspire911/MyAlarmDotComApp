package com.mdmx.myalarmdotcomapp.util

import android.util.Log
import com.mdmx.myalarmdotcomapp.util.Constant.TAG


class Logger(private val isEnabled: Boolean) {

    fun e(message: String) {
        if(isEnabled) Log.e(TAG, message)
    }

    fun w(message: String) {
        if(isEnabled) Log.w(TAG, message)
    }

    fun v(message: String) {
        if(isEnabled) Log.v(TAG, message)
    }

    fun d(message: String) {
        if(isEnabled) Log.d(TAG, message)
    }
}