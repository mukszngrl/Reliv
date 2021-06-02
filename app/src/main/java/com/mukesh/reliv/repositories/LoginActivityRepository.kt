package com.mukesh.reliv.repositories

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mukesh.reliv.model.GenerateOTPResponseDO
import com.mukesh.reliv.model.RegistrationRequestDO
import com.mukesh.reliv.model.UserDetailsResponse
import com.mukesh.reliv.model.ValidateOTPResponseDO
import com.mukesh.reliv.retrofit.Resource
import com.mukesh.reliv.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LoginActivityRepository {

    private val generateOTPResponse = MutableLiveData<Resource<GenerateOTPResponseDO>>()
    private val validateOTPResponse = MutableLiveData<Resource<ValidateOTPResponseDO>>()
    private val userDetailsResponse = MutableLiveData<Resource<UserDetailsResponse>>()

    fun generateUserOTP(
        name: String,
        mobNo: String
    ): MutableLiveData<Resource<GenerateOTPResponseDO>> {

        val call = RetrofitClient.apiInterface.generateUserOTP(name = name, mobileNo = mobNo)

        call.enqueue(object : Callback<GenerateOTPResponseDO> {
            override fun onFailure(call: Call<GenerateOTPResponseDO>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<GenerateOTPResponseDO>,
                response: Response<GenerateOTPResponseDO>
            ) {
                Log.v("DEBUG : ", response.body().toString())

                val data = response.body()
                if (data != null)
                    generateOTPResponse.value = Resource.success(data)
                else
                    generateOTPResponse.value =
                        Resource.error(response.errorBody()?.string() ?: "Server Error", data)
            }
        })

        return generateOTPResponse
    }

    fun validateUserOTP(mobNo: String, otp: String)
            : MutableLiveData<Resource<ValidateOTPResponseDO>> {
        val call = RetrofitClient.apiInterface.validateUserOTP(mobileNo = mobNo, otp = otp)

        call.enqueue(object : Callback<ValidateOTPResponseDO> {
            override fun onFailure(call: Call<ValidateOTPResponseDO>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<ValidateOTPResponseDO>,
                response: Response<ValidateOTPResponseDO>
            ) {
                Log.v("DEBUG : ", response.body().toString())

                val data = response.body()

                if (data != null)
                    validateOTPResponse.value = Resource.success(data)
                else
                    validateOTPResponse.value =
                        Resource.error(response.errorBody()?.string() ?: "Server Error", data)
            }
        })

        return validateOTPResponse
    }

    fun getUserDetails(): MutableLiveData<Resource<UserDetailsResponse>> {
        val call = RetrofitClient.apiInterface.getUserDetails()

        call.enqueue(object : Callback<UserDetailsResponse> {
            override fun onFailure(call: Call<UserDetailsResponse>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<UserDetailsResponse>,
                response: Response<UserDetailsResponse>
            ) {
                Log.v("DEBUG : ", response.body().toString())

                val data = response.body()

                if (data != null)
                    userDetailsResponse.value = Resource.success(data)
                else
                    userDetailsResponse.value =
                        Resource.error(response.errorBody()?.string() ?: "Server Error", data)
            }
        })

        return userDetailsResponse
    }

    fun signUp(signUpDO: RegistrationRequestDO): MutableLiveData<Resource<UserDetailsResponse>> {
        val call = RetrofitClient.apiInterface.signUp(RetrofitClient.getHeaders(), signUpDO)

        call.enqueue(object : Callback<UserDetailsResponse> {
            override fun onFailure(call: Call<UserDetailsResponse>, t: Throwable) {
                Log.v("DEBUG : ", t.message.toString())
            }

            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<UserDetailsResponse>,
                response: Response<UserDetailsResponse>
            ) {
                Log.v("DEBUG : ", response.body().toString())

                val data = response.body()

                if (data != null) {
                    userDetailsResponse.value = Resource.success(data)
                } else {
                    userDetailsResponse.value =
                        Resource.error(response.errorBody()?.string() ?: "Server Error", data)
                }
            }
        })

        return userDetailsResponse
    }
}