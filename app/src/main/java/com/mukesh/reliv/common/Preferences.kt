package com.mukesh.reliv.common

import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences

@SuppressLint("CommitPrefEdits")
object Preferences {
    private const val PREFS_FILENAME = "com.mukesh.reliv.prefs"
    private var preferences: SharedPreferences? = null
    private var edit: SharedPreferences.Editor? = null
    const val USERNAME: String = "USERNAME"
    const val MOBILE_NO: String = "MOBILE_NO"

    fun init(application: Application) {
        preferences = application.getSharedPreferences(PREFS_FILENAME, 0)
        edit = preferences?.edit()
    }

    fun saveStringInPreference(strKey: String, strValue: String?) {
        edit?.let {
            edit!!.putString(strKey, strValue)
            applyPreference()
        }
    }

    fun getStringFromPreference(strKey: String?, defaultValue: String?): String? {
        return preferences?.let { preferences!!.getString(strKey, defaultValue) }
    }

    private fun applyPreference() {
        edit!!.apply()
    }

    fun clearPreferences() {
        edit!!.clear()
        applyPreference()
    }
}