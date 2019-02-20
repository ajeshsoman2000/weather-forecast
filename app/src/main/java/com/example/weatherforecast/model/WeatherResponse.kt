package com.example.weatherforecast.model

sealed class WeatherResponse {
    class Success(val result: WeatherModel): WeatherResponse()
    class Error(val message: String): WeatherResponse()
}