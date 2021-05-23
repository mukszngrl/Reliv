package com.mukesh.reliv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mukesh.reliv.model.ServicesSetterGetter
import com.mukesh.reliv.model.WeatherGetterSetter
import com.mukesh.reliv.repositories.MainActivityRepository

class MainActivityViewModel : ViewModel() {

    var servicesLiveData: MutableLiveData<ServicesSetterGetter>? = null
    var weatherLiveData: MutableLiveData<WeatherGetterSetter>? = null

    fun getUser() : LiveData<ServicesSetterGetter>? {
        servicesLiveData = MainActivityRepository.getServicesApiCall()
        return servicesLiveData
    }

    fun getWeather() : LiveData<WeatherGetterSetter>? {
        weatherLiveData = MainActivityRepository.getWeatherApiCall()
        return weatherLiveData
    }

}