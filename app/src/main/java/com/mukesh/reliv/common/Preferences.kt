package com.mukesh.reliv.common

import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mukesh.reliv.model.SignUpDO
import java.util.*

@SuppressLint("CommitPrefEdits")
object Preferences {
    private const val PREFS_FILENAME = "com.mukesh.reliv.prefs"
    private var preferences: SharedPreferences? = null
    private var edit: SharedPreferences.Editor? = null
    const val USERNAME: String = "USERNAME"
    const val MOBILE_NO: String = "MOBILE_NO"
    const val FCM_TOKEN: String = "FCM_TOKEN"

    fun init(application: Application) {
        preferences = application.getSharedPreferences(PREFS_FILENAME, 0)
        edit = preferences?.edit()
    }

    fun saveStringInPreference(strKey: String, strValue: String?) {
        edit?.let {
            edit?.putString(strKey, strValue)
            applyPreference()
        }
    }

    fun getStringFromPreference(strKey: String?, defaultValue: String?): String? {
        return preferences?.let { preferences?.getString(strKey, defaultValue) }
    }

    fun saveUserInHashMap(myMap: HashMap<String, SignUpDO>) {
        edit?.let {
            val converted = Gson().toJson(myMap)
            edit?.putString("USER_HASH_MAP", converted)
            applyPreference()
        }
    }

    fun getUserHashMap(): HashMap<String, SignUpDO>? {
        val hashString = preferences?.let { preferences?.getString("USER_HASH_MAP", null) }
        return Gson().fromJson(hashString, object : TypeToken<HashMap<String?, SignUpDO>>() {}.type)
    }

    private fun applyPreference() {
        edit!!.apply()
    }

    fun clearPreferences() {
        edit!!.clear()
        applyPreference()
    }
}