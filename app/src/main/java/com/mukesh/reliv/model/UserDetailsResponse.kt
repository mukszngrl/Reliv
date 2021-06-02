package com.mukesh.reliv.model

data class UserDetailsResponse(
    val Data: UserDO,
    val status: String,
    val statusCode: Int,
    val statusMessage: String
)
