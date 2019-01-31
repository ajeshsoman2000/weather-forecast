package com.example.weatherforecast

interface UpdateUIListener {
    fun stopProgressbar()
    fun notifyError(message: String)
}