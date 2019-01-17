package com.example.weatherforecast.model

class WeatherModel(val forecast: ForeCasts)

data class ForeCasts(val forecastday: List<Forecast>)

data class Forecast(val date: String, val day: Day)

data class Day(val maxtemp_c: Double, val mintemp_c: Double, val condition: Condition)

data class Condition(val text: String, val icon: String)