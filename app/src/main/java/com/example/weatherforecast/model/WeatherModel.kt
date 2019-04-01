package com.example.weatherforecast.model

data class WeatherModel(val forecast: ForeCasts)

data class ForeCasts(val forecastday: List<Forecast>)

data class Forecast(val date: String, val day: Day)

data class Day(val maxtemp_c: Double, val maxtemp_f: Double,
               val mintemp_c: Double,val mintemp_f: Double,
               val condition: Condition, val avgvis_km: Double,
               val maxwind_mph: Double, val maxwind_kph: Double,
               val avgvis_miles: Double, val avghumidity: Double)

data class Condition(val text: String, val icon: String)