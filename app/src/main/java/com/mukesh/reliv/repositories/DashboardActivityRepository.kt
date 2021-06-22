package com.mukesh.reliv.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mukesh.reliv.model.GetScheduleDetailsResponse
import com.mukesh.reliv.model.MesiboUserTokenResponse
import com.mukesh.reliv.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DashboardActivityRepository {

    val mesiboTokenResponse = MutableLiveData<MesiboUserTokenResponse>()
    val scheduleDetailsResponse = MutableLiveData<GetScheduleDetailsResponse>()

    fun getMesiboUserToken(mobNo: String): MutableLiveData<MesiboUserTokenResponse> {

        val call = RetrofitClient.apiInterfaceMesibo.getMesiboUserToken(addr = mobNo)

        call.enqueue(object : Callback<MesiboUserTokenResponse> {
            override fun onFailure(call: Call<MesiboUserTokenResponse>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<MesiboUserTokenResponse>,
                response: Response<MesiboUserTokenResponse>
            ) {
                Log.v("DEBUG : ", response.body().toString())

                val data = response.body()

                mesiboTokenResponse.value = data!!
            }
        })

        return mesiboTokenResponse
    }

    fun getScheduledDetails(): MutableLiveData<GetScheduleDetailsResponse>? {
        val call = RetrofitClient.apiInterface.getScheduledDetails(RetrofitClient.getHeaders())

        call.enqueue(object : Callback<GetScheduleDetailsResponse> {
            override fun onFailure(call: Call<GetScheduleDetailsResponse>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            override fun onResponse(
                call: Call<GetScheduleDetailsResponse>,
                response: Response<GetScheduleDetailsResponse>
            ) {
                Log.v("DEBUG : ", response.body().toString())

                val data = response.body()

                scheduleDetailsResponse.value = data!!
            }
        })

        return scheduleDetailsResponse
    }
}