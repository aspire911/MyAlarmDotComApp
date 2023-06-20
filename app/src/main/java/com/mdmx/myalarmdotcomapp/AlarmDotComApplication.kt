package com.mdmx.myalarmdotcomapp

import android.app.Application
import com.mdmx.myalarmdotcomapp.util.Logger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AlarmDotComApplication : Application() {
    companion object {
        lateinit var cookies: Map<String, String> // cookies after login in
        val logger = Logger(true) // TRUE = logger ON, FALSE = logger OFF
    }

}