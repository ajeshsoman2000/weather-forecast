package com.example.weatherforecast.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.ActivityMainBinding
import com.example.weatherforecast.model.WeatherModel
import com.example.weatherforecast.viewmodel.MainViewModel
import com.example.weatherforecast.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var weatherDetail: MutableLiveData<WeatherModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val activityMainBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this@MainActivity,
            R.layout.activity_main)
        activityMainBinding.viewModel = ViewModelProviders.of(this@MainActivity, ViewModelFactory()).
            get(MainViewModel::class.java)

        btn_forecast.setOnClickListener {
            if (et_city.text.toString().trim().isNotEmpty()) {
                weatherDetail = (activityMainBinding.viewModel as MainViewModel).getWeatherForecast(et_city.text.toString())
                setObserver()
            } else {
                Toast.makeText(this@MainActivity, "Please enter a city.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setObserver(){
        (weatherDetail as MutableLiveData<WeatherModel>).observe(this@MainActivity, object : Observer<WeatherModel> {
            override fun onChanged(t: WeatherModel?) {
                t?.forecast?.forecastday?.forEach {
                    Log.v("MainActivity", "forecast dates => ${it.date}")
                }
            }

        })
    }
}
