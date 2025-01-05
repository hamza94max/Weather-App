package com.hamza.core.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateFormatter {

    fun getCurrentDateTime(): String {
        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("EEEE, MMM d, hh:mm a")
        return now.format(formatter)
    }


    fun getDayName(dateString: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val localDateTime = LocalDateTime.parse(dateString, formatter)
        val localDate = localDateTime.toLocalDate()
        val dayNameFormatter = DateTimeFormatter.ofPattern("EEEE")
        return localDate.format(dayNameFormatter)
    }


}