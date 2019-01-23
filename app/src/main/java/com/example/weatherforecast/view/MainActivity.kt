package com.example.weatherforecast.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecast.R
import com.example.weatherforecast.UpdateUIListener
import com.example.weatherforecast.databinding.ActivityMainBinding
import com.example.weatherforecast.model.Forecast
import com.example.weatherforecast.model.WeatherModel
import com.example.weatherforecast.utils.isNetworkConnectionAvailable
import com.example.weatherforecast.view.adapters.WeatherForecastRecyclerViewAdapter
import com.example.weatherforecast.viewmodel.MainViewModel
import com.example.weatherforecast.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var weatherDetail: LiveData<WeatherModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this@MainActivity,
            R.layout.activity_main)

        activityMainBinding.viewModel =
                    ViewModelProviders.of(this@MainActivity, ViewModelFactory()).get(MainViewModel::class.java)


        rv_weather_forecast.layoutManager = LinearLayoutManager(this@MainActivity)
        rv_weather_forecast.itemAnimator = DefaultItemAnimator()

        if ((activityMainBinding as ActivityMainBinding).viewModel?.weatherDetail != null ) {
            rv_weather_forecast.adapter = WeatherForecastRecyclerViewAdapter(
                this@MainActivity,
                activityMainBinding.viewModel?.weatherDetail?.value?.forecast?.forecastday!!
            )
        }


        btn_forecast.setOnClickListener {
            if(this@MainActivity.isNetworkConnectionAvailable()) {
                if (et_city.text.toString().trim().isNotEmpty()) {
                    pb_fetch_forecast.visibility = View.VISIBLE
                    btn_forecast.isEnabled = false
                    weatherDetail =
                            (activityMainBinding.viewModel as MainViewModel).getWeatherForecast(et_city.text.toString(),
                                object : UpdateUIListener {
                                    override fun stopProgressbar() {
                                        pb_fetch_forecast.visibility = View.GONE
                                        btn_forecast.isEnabled = true
                                    }

                                })
                    setObserver()
                } else {
                    Toast.makeText(this@MainActivity, "Please enter a city.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@MainActivity, "Please ensure network connectivity.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setObserver(){
        (weatherDetail as LiveData<WeatherModel>).observe(this@MainActivity,
            Observer<WeatherModel> { t ->
                rv_weather_forecast.adapter = WeatherForecastRecyclerViewAdapter(this@MainActivity,
                    t?.forecast?.forecastday as List<Forecast>)
            })
    }
}
