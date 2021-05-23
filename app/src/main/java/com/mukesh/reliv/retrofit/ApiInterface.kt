package com.mukesh.reliv.retrofit

import com.mukesh.reliv.model.ServicesSetterGetter
import com.mukesh.reliv.model.WeatherGetterSetter
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("services")
    fun getServices() : Call<ServicesSetterGetter>

    @GET("/v2/5d3a99ed2f0000bac16ec13a")
    fun getWeatherReport(): Call<WeatherGetterSetter>

}