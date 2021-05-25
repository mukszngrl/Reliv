package com.mukesh.reliv.application

import androidx.multidex.MultiDexApplication
import com.mukesh.reliv.common.AppConstants
import com.mukesh.reliv.common.Preferences

class RelivApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Preferences.init(this)
        AppConstants.initializeTypeFace(this)
    }
}