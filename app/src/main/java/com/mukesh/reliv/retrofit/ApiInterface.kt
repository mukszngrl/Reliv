package com.mukesh.reliv.retrofit

import com.mukesh.reliv.BuildConfig
import com.mukesh.reliv.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("services")
    fun getServices(): Call<ServicesSetterGetter>

    @GET("GetUserDetails")
    fun getUserDetails(): Call<UserDetailsResponse>

    @GET("GenerateUserOTP")
    fun generateUserOTP(
        @Query("Name") name: String,
        @Query("MobileNo") mobileNo: String
    ): Call<GenerateOTPResponseDO>

    @GET("ValidateUserOTP")
    fun validateUserOTP(
        @Query("MobileNo") mobileNo: String,
        @Query("otp") otp: String
    ): Call<ValidateOTPResponseDO>

    @GET("GetScheduledDetails")
    fun getScheduledDetails(
        @HeaderMap headers: HashMap<String, String>
    ): Call<GetScheduleDetailsResponse>

    @POST("SignUp")
    fun signUp(
        @HeaderMap headers: HashMap<String, String>,
        @Body signUpDO: RegistrationRequestDO
    ): Call<UserDetailsResponse>

    @GET("GenerateOrderForRazorPay")
    fun generateOrderForRazorPay(
        @Query("ReceiptId") receiptID: String,
        @Query("Amount") amount: String
    ): Call<GenerateRazorPayOrderDO>

    @GET("api.php")
    fun getMesiboUserToken(
        @Query("token") token: String = BuildConfig.MESIBO_APP_TOKEN,
        @Query("op") op: String = "useradd",
        @Query("appid") appID: String = BuildConfig.APPLICATION_ID,
        @Query("addr") addr: String
    ): Call<MesiboUserTokenResponse>

    @GET("/v2/5d3a99ed2f0000bac16ec13a")
    fun getWeatherReport(): Call<WeatherGetterSetter>

}