package com.example.weatherforecast.repository.service

import com.example.weatherforecast.model.WeatherModel
import com.example.weatherforecast.model.WeatherResponse
import com.google.gson.Gson
import java.lang.Exception
import java.net.URL
import java.net.URLEncoder
import javax.net.ssl.HttpsURLConnection


class WeatherForecastService {
    private val BASE_URL = "https://api.apixu.com"

    suspend fun fetch(city: String): WeatherResponse {
        return fetchForecast(city)
    }

    private suspend fun fetchForecast(city: String): WeatherResponse {
        val url = "$BASE_URL/v1/forecast.json?key=c7227658949e4dd7a6a135138191701&q=${URLEncoder.encode(city, "utf-8")}&days=5"
        val connection = URL(url).openConnection() as HttpsURLConnection
        return try {
            val data = connection.inputStream.bufferedReader().readText()
            val resultObj = Gson().fromJson(data, WeatherModel::class.java)
            WeatherResponse.Success(resultObj)
        } catch (e: Exception) {
            WeatherResponse.Error(connection.responseMessage)
        }
    }
}