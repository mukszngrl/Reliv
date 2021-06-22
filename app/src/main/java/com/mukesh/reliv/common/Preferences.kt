package com.mukesh.reliv.common

import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import com.google.gson.Gson

@SuppressLint("CommitPrefEdits")
object Preferences {
    const val DOCTOR_OBJECT: String = "DOCTOR_OBJECT"
    const val IS_PAYMENT_DONE: String = "IS_PAYMENT_DONE"
    const val USER_DETAILS_DO: String = "USER_DETAILS_DO"
    const val USER_TYPE: String = "USER_TYPE"
    const val USER_ID: String = "USER_ID"
    const val GUID_TOKEN: String = "GUID_TOKEN"
    const val MESIBO_USER_TOKEN: String = "MESIBO_USER_TOKEN"
    const val LAST_MESIBO_USER: String = "LAST_MESIBO_USER"
    const val MESIBO_USER_TOKEN_RESPONSE_OBJECT: String = "MESIBO_USER_TOKEN_RESPONSE_OBJECT"
    private const val PREFS_FILENAME = "com.mukesh.reliv.prefs"
    var preferences: SharedPreferences? = null
    private var edit: SharedPreferences.Editor? = null
    const val USERNAME: String = "USERNAME"
    const val MOBILE_NO: String = "MOBILE_NO"
    const val FCM_TOKEN: String = "FCM_TOKEN"
    const val USER_HASH_MAP: String = "USER_HASH_MAP"
    const val IS_LOGGED_IN: String = "IS_LOGGED_IN"

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

    fun saveBooleanInPreference(strKey: String, strValue: Boolean) {
        edit?.let {
            edit?.putBoolean(strKey, strValue)
            applyPreference()
        }
    }

    fun getBooleanFromPreference(strKey: String?, defaultValue: Boolean): Boolean? {
        return preferences?.let { preferences?.getBoolean(strKey, defaultValue) }
    }
    /*fun saveUserInHashMap(myMap: HashMap<String, SignUpDO>) {
        edit?.let {
            val converted = Gson().toJson(myMap)
            edit?.putString(USER_HASH_MAP, converted)
            applyPreference()
        }
    }

    fun getUserHashMap(): HashMap<String, SignUpDO>? {
        val hashString = preferences?.let { preferences?.getString(USER_HASH_MAP, null) }
        return Gson().fromJson(hashString, object : TypeToken<HashMap<String?, SignUpDO>>() {}.type)
    }*/

    fun <T> saveObjectInPreference(key: String, data: T) {
        edit?.let {
            val converted = Gson().toJson(data)
            edit?.putString(key, converted)
            applyPreference()
        }
    }

    inline fun <reified T> getObjectFromPreference(key: String): T? {
        val hashString = preferences?.let { preferences?.getString(key, null) }
        return Gson().fromJson(hashString, T::class.java)
    }

    private fun applyPreference() {
        edit!!.apply()
    }

    fun clearPreferences() {
        edit!!.clear()
        applyPreference()
    }
}