package com.mukesh.reliv.common

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object CalendarUtils {

    const val YYYY_MM_DD_T_HH_MM_SS_PATTERN = "yyyy-MM-dd'T'hh:mm:ss"
    const val DD_MMM_YYYY_HH_MM_A_PATTERN = "dd MMM yyyy hh:mm a"
    const val DD_PATTERN = "dd"
    const val MM_PATTERN = "MM"
    const val YYYY_PATTERN = "yyyy"

    fun getCurrentDateToFormat(toPattern: String?): String? {
        val date: String?
        val sdf = SimpleDateFormat(toPattern, Locale.ENGLISH)
        date = sdf.format(Date())
        return date
    }

    fun changeDateFormat(inputDateStr: String?, fromPattern: String?, toPattern: String?): String? {
        return try {
            val inputFormat: DateFormat = SimpleDateFormat(fromPattern, Locale.ENGLISH)
            val outputFormat: DateFormat = SimpleDateFormat(toPattern, Locale.ENGLISH)
            val date: Date? = inputDateStr?.let { inputFormat.parse(inputDateStr) }
            date?.let { outputFormat.format(date) }
        } catch (e: Exception) {
            e.printStackTrace()
            inputDateStr
        }
    }
}