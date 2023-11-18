package com.achsanit.simpleweather.foundation.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateHelper {

    fun formatCurrentMillisTime(currentMillis: Long): String {
        return try {
            val result = Date(currentMillis)
            val format = SimpleDateFormat("HH:mm", Locale.getDefault())
            format.format(result)
        } catch (e: Exception) {
            e.message ?: "Error"
        }
    }

    fun formatToDate(currentMillis: Long): String {
        return try {
            val result = Date(currentMillis)
            val format = SimpleDateFormat("EEEE, MMMM dd HH:mm", Locale.getDefault())
            format.format(result)
        } catch (e: Exception) {
            e.message ?: "Error"
        }
    }
}