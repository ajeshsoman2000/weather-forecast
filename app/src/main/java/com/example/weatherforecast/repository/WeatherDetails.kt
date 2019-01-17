package com.example.weatherforecast.repository

import androidx.lifecycle.MutableLiveData
import com.example.weatherforecast.model.WeatherModel

object WeatherDetails {
    var weatherDetail = MutableLiveData<WeatherModel>()
}