package com.example.weatherforecast.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

fun Context.isNetworkConnectionAvailable(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    return cm?.activeNetworkInfo != null
}