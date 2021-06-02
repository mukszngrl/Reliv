package com.mukesh.reliv.application

import android.annotation.SuppressLint
import android.content.Context
import androidx.multidex.MultiDexApplication
import com.mukesh.reliv.common.AppConstants
import com.mukesh.reliv.common.Preferences

class RelivApplication : MultiDexApplication() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var mContext: Context? = null
    }

    override fun onCreate() {
        super.onCreate()

        mContext = this;
        Preferences.init(this)
        AppConstants.initializeTypeFace(this)
    }
}