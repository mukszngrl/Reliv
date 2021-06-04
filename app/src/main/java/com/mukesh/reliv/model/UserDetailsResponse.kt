package com.mukesh.reliv.model

data class UserDetailsResponse(
    val Data: UserRequestDO,
    val status: String,
    val statusCode: Int,
    val statusMessage: String
)
