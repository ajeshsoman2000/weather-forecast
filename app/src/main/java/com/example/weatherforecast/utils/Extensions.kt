package com.example.weatherforecast.utils

import android.content.Context
import java.text.SimpleDateFormat
import android.net.NetworkInfo
import androidx.core.content.ContextCompat.getSystemService
import android.net.ConnectivityManager
import android.text.format.DateFormat


fun String.changeDateFormat(fromFormat: String, toFormat: String): String {
    val format1 = SimpleDateFormat(fromFormat)
    val date = format1.parse(this)
    val format2 = SimpleDateFormat(toFormat)
    return format2.format(date)
}

fun Context.isNetworkConnectionAvailable(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val info = cm!!.activeNetworkInfo ?: return false
    val network = info.state
    return network === NetworkInfo.State.CONNECTED || network === NetworkInfo.State.CONNECTING
}

fun String.getDayFromDate(fromFormat: String): String {
    val format = SimpleDateFormat(fromFormat)
    val date = format.parse(this)
    return DateFormat.format("EEEE", date).toString()
}