package com.mukesh.reliv.retrofit

import com.mukesh.reliv.BuildConfig
import com.mukesh.reliv.common.Preferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val MAIN_URL = "http://independentr.com/api/Relive/"
    private const val ImageUploadURL = "http://independentr.com/ReliveApp/UploadImage.aspx"
    private const val MESIBO_SERVER = "https://api.mesibo.com"

    private val retrofitClient: Retrofit.Builder by lazy {

        val levelType: Level = if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
            Level.BODY else Level.NONE

        val logging = HttpLoggingInterceptor()
        logging.setLevel(levelType)

        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(logging)

        Retrofit.Builder()
            .baseUrl(MAIN_URL)
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: ApiInterface by lazy {
        retrofitClient
            .build()
            .create(ApiInterface::class.java)
    }

    private val retrofitClientMesibo: Retrofit.Builder by lazy {

        val levelType: Level = if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
            Level.BODY else Level.NONE

        val logging = HttpLoggingInterceptor()
        logging.setLevel(levelType)

        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(logging)

        Retrofit.Builder()
            .baseUrl(MESIBO_SERVER)
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterfaceMesibo: ApiInterface by lazy {
        retrofitClientMesibo
            .build()
            .create(ApiInterface::class.java)
    }

    fun getHeaders(): HashMap<String, String> {
        val headerHashMap: HashMap<String, String> = HashMap()
        headerHashMap["Content-Type"] = "application/json"
        headerHashMap["UserId"] =
            Preferences.getStringFromPreference(Preferences.USER_ID, "0").toString()
        headerHashMap["Token"] =
            Preferences.getStringFromPreference(Preferences.GUID_TOKEN, "").toString()
        headerHashMap["UserType"] =
            Preferences.getStringFromPreference(Preferences.USER_TYPE, "Patient").toString()
        return headerHashMap
    }
}