package com.example.weatherforecast.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

fun Context.isNetworkConnectionAvailable(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val info = cm!!.activeNetworkInfo ?: return false
    val network = info.state
    return network === NetworkInfo.State.CONNECTED || network === NetworkInfo.State.CONNECTING
}