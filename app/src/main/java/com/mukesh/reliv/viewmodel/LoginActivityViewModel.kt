package com.mukesh.reliv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mukesh.reliv.model.*
import com.mukesh.reliv.repositories.LoginActivityRepository

class LoginActivityViewModel : ViewModel() {

    private var validateUserOTPLiveData: MutableLiveData<ValidateOTPResponseDO>? = null
    private var generateOTPLiveData: MutableLiveData<GenerateOTPResponseDO>? = null
    private var userDetails: MutableLiveData<UserDetailsResponse>? = null

    fun generateUserOTP(name: String, mobNo: String): MutableLiveData<GenerateOTPResponseDO>? {
        generateOTPLiveData = LoginActivityRepository.generateUserOTP(name, mobNo)
        return generateOTPLiveData
    }

    fun validateUserOTP(mobNo: String, otp: String): LiveData<ValidateOTPResponseDO>? {
        validateUserOTPLiveData = LoginActivityRepository.validateUserOTP(mobNo, otp)
        return validateUserOTPLiveData
    }

    fun getUserDetails(): LiveData<UserDetailsResponse>? {
        userDetails = LoginActivityRepository.getUserDetails()
        return userDetails
    }

    fun signUp(signUpDO: RegistrationRequestDO): LiveData<UserDetailsResponse>? {
        userDetails = LoginActivityRepository.signUp(signUpDO)
        return userDetails
    }
}