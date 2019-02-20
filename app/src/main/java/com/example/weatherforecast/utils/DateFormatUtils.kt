package com.example.weatherforecast.utils

import android.text.format.DateFormat
import java.text.SimpleDateFormat

fun String.changeDateFormat(fromFormat: String, toFormat: String): String {
    val format1 = SimpleDateFormat(fromFormat)
    val date = format1.parse(this)
    val format2 = SimpleDateFormat(toFormat)
    return format2.format(date)
}

fun String.getDayFromDate(fromFormat: String): String {
    val format = SimpleDateFormat(fromFormat)
    val date = format.parse(this)
    return DateFormat.format("EEEE", date).toString()
}