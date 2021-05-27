package com.mukesh.reliv.model

import java.io.Serializable

data class SignUpDO(
    val mobileNo: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val profileImagePath: String = "",
    val gender: String = "",
    val height: String = "",
    val weight: String = "",
    val dateOfBirth: String = "",
    val address: String = ""
) : Serializable
