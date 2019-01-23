package com.example.weatherforecast.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecast.UpdateUIListener
import com.example.weatherforecast.model.WeatherModel
import com.example.weatherforecast.repository.WeatherRepository

class MainViewModel: ViewModel() {

    var weatherDetail: LiveData<WeatherModel>? = null

    fun getWeatherForecast(city: String, listener: UpdateUIListener): LiveData<WeatherModel> {
        weatherDetail = WeatherRepository().getWeatherForecast(city, listener)
        return weatherDetail as LiveData<WeatherModel>
//        return WeatherDetails.weatherDetail
    }


}