package com.mukesh.reliv.application

import android.app.Application
import com.mukesh.reliv.common.Preferences

class RelivApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Preferences.init(this)
    }
}