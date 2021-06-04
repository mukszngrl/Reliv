package com.mukesh.reliv.model

data class SignUpResponseDO(
    val Data: UserDO,
    val status: String,
    val statusCode: Int,
    val statusMessage: String
)
