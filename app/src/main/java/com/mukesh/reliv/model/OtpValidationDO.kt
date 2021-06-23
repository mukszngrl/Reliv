package com.mukesh.reliv.model

data class OtpValidationDO(
    val IsRegisterUser: Boolean,
    val Token: String,
    val IsPatient: Boolean,
    val IsDoctor: Boolean,
    val DoctorDetails: PatientScheduleTimingsDO,
    val PatientDetails: UserDO
)
