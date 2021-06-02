package com.mukesh.reliv.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mukesh.reliv.model.ServicesSetterGetter
import com.mukesh.reliv.model.WeatherGetterSetter
import com.mukesh.reliv.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MainActivityRepository {

    val serviceSetterGetter = MutableLiveData<ServicesSetterGetter>()
    val weatherGetterSetter = MutableLiveData<WeatherGetterSetter>()

    fun getServicesApiCall(): MutableLiveData<ServicesSetterGetter> {

        val call = RetrofitClient.apiInterface.getServices()

        call.enqueue(object : Callback<ServicesSetterGetter> {
            override fun onFailure(call: Call<ServicesSetterGetter>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<ServicesSetterGetter>,
                response: Response<ServicesSetterGetter>
            ) {
                Log.v("DEBUG : ", response.body().toString())

                val data = response.body()

                val msg = data!!.message

                serviceSetterGetter.value = ServicesSetterGetter(msg)
            }
        })

        return serviceSetterGetter
    }

    fun getWeatherApiCall(): MutableLiveData<WeatherGetterSetter> {

        val call = RetrofitClient.apiInterface.getWeatherReport()

        call.enqueue(object : Callback<WeatherGetterSetter> {
            override fun onFailure(call: Call<WeatherGetterSetter>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<WeatherGetterSetter>,
                response: Response<WeatherGetterSetter>
            ) {
                Log.v("DEBUG : ", response.body().toString())
                val data = response.body()

                weatherGetterSetter.value = data!!
            }
        })

        return weatherGetterSetter
    }
}