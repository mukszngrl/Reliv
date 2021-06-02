package com.mukesh.reliv.model

data class ValidateOTPResponseDO(
    val Data: OtpValidationDO,
    val status: String,
    val statusCode: Int,
    val statusMessage: String
)
