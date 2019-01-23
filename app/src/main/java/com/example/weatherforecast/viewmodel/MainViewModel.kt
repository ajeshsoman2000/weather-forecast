package com.example.weatherforecast.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecast.UpdateUIListener
import com.example.weatherforecast.model.WeatherModel
import com.example.weatherforecast.repository.WeatherDetails
import com.example.weatherforecast.repository.WeatherRepository

class MainViewModel: ViewModel() {

    fun getWeatherForecast(city: String, listener: UpdateUIListener): MutableLiveData<WeatherModel> {
        WeatherRepository().getWeatherForecast(city, listener)
        return WeatherDetails.weatherDetail
    }
}