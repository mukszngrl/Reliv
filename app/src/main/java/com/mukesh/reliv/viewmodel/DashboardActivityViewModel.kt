package com.mukesh.reliv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mukesh.reliv.model.MesiboUserTokenResponse
import com.mukesh.reliv.repositories.DashboardActivityRepository

class DashboardActivityViewModel : ViewModel() {

    var servicesLiveData: MutableLiveData<MesiboUserTokenResponse>? = null

    fun getMesiboUserToken(mobNo: String): LiveData<MesiboUserTokenResponse>? {
        servicesLiveData = DashboardActivityRepository.getMesiboUserToken(mobNo)
        return servicesLiveData
    }
}