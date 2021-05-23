package com.mukesh.reliv.common

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object CalendarUtils {

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