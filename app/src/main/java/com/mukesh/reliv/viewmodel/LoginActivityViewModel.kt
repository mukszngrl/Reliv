package com.mukesh.reliv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.mukesh.reliv.model.*
import com.mukesh.reliv.repositories.LoginActivityRepository
import com.mukesh.reliv.retrofit.Resource

class LoginActivityViewModel : ViewModel() {

    private var validateUserOTPLiveData: LiveData<Resource<ValidateOTPResponseDO>>? = null
    private var generateOTPLiveData: LiveData<Resource<GenerateOTPResponseDO>>? = null
    private var userDetails: LiveData<Resource<UserDetailsResponse>>? = null
    private var userDetailsSignUp: LiveData<Resource<UserDetailsResponse>>? = null

    fun generateUserOTP(name: String, mobNo: String)
            : LiveData<Resource<GenerateOTPResponseDO>> {
        generateOTPLiveData = LoginActivityRepository.generateUserOTP(name, mobNo)
        return Transformations.distinctUntilChanged(generateOTPLiveData as MutableLiveData<Resource<GenerateOTPResponseDO>>)
    }

    fun validateUserOTP(mobNo: String, otp: String): LiveData<Resource<ValidateOTPResponseDO>> {
        validateUserOTPLiveData = LoginActivityRepository.validateUserOTP(mobNo, otp)
        return Transformations.distinctUntilChanged(validateUserOTPLiveData as MutableLiveData<Resource<ValidateOTPResponseDO>>)
    }

    fun getUserDetails(): LiveData<Resource<UserDetailsResponse>> {
        userDetails = LoginActivityRepository.getUserDetails()
        return Transformations.distinctUntilChanged(userDetails as MutableLiveData<Resource<UserDetailsResponse>>)
    }

    fun signUp(signUpDO: RegistrationRequestDO): LiveData<Resource<UserDetailsResponse>> {
        userDetailsSignUp = LoginActivityRepository.signUp(signUpDO)
        return Transformations.distinctUntilChanged(userDetailsSignUp as MutableLiveData<Resource<UserDetailsResponse>>)
    }
}