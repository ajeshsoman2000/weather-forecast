package com.example.weatherforecast.repository.service

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.weatherforecast.UpdateUIListener
import com.example.weatherforecast.model.WeatherModel
import com.example.weatherforecast.repository.WeatherDetails
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import okhttp3.OkHttpClient



class WeatherForecastService: Callback<WeatherModel> {
    private val BASE_URL = "https://api.apixu.com"
    private lateinit var updateUIListener: UpdateUIListener

    override fun onFailure(call: Call<WeatherModel>, t: Throwable) {
        Log.v("WeatherForecastService", "onfailure => ${t.message}")
        updateUIListener.stopProgressbar()
    }

    override fun onResponse(call: Call<WeatherModel>, response: Response<WeatherModel>) {
        updateUIListener.stopProgressbar()
        if (response.isSuccessful) {
            Log.v("WeatherForecastService", "onResponse :: isSuccessful => ${response.body()}")
            WeatherDetails.weatherDetail.value = response.body()
        } else {
            Log.v("WeatherForecastService", "onResponse :: isNotSuccessful :: message => ${response.message()} and error code :: ${response.code()}")
        }
    }

    fun fetch(city: String, listener: UpdateUIListener) {
        Log.v("Forecast", "for city :: $city")
        updateUIListener = listener
        val okHttpClient = OkHttpClient.Builder().addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                val originalRequest = chain.request()

                Log.v("okHttpClient :: ", "request details == url == ${originalRequest.url()}")
                Log.v("okHttpClient :: ", "request details == body == ${originalRequest.body()}")

                return chain.proceed(originalRequest)
            }
        }).build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val forecastServiceInterface = retrofit.create(WeatherForecastServiceInterface::class.java)

        val call: Call<WeatherModel> = forecastServiceInterface.getForecastForCity(city)
        call.enqueue(this)

    }

    interface WeatherForecastServiceInterface {

//        /v1/forecast.json?key=c7227658949e4dd7a6a135138191701&q=Chennai&days=5
        @GET("/v1/forecast.json?key=c7227658949e4dd7a6a135138191701")
        fun getForecastForCity(@Query("q") city: String, @Query("days") days: String = "5"): Call<WeatherModel>
    }
}