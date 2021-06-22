package com.mukesh.reliv.model

data class DoctorScheduleTimingsDO(
    val SchedulerTime: String,
    val Dd_About: String,
    val Dd_City: String,
    val Dd_Deparment: String,
    val Dd_Designation: String,
    val Dd_Email: String,
    val Dd_Gender: String,
    val Dd_id: Long,
    val Dd_Languages: String,
    val Dd_Location: String,
    val Dd_Medical_reg: String,
    val Dd_Medical_reg_image: Any? = null,
    val Dd_Medical_Reg_no: String,
    val Dd_Name: String,
    val Dd_Phone: String,
    val Dd_Prefix: String,
    val Dd_Profile: String,
    val Dd_Status: Long,
    val Dd_Years_Of_Experience: Long,
    val SchedulingTimes: String
)
