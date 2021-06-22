package com.mukesh.reliv.model

data class GetScheduleDetailsResponse(
    val Data: DataDO,
    val status: String,
    val statusCode: Int,
    val statusMessage: String
)

data class DataDO(
    val DoctorSchedulingTimings: ArrayList<DoctorScheduleTimingsDO>,
    val PatientSchedulingTimings: ArrayList<PatientScheduleTimingsDO>
)
