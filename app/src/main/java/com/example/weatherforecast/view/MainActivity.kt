package com.example.weatherforecast.view

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.ActivityMainBinding
import com.example.weatherforecast.model.WeatherModel
import com.example.weatherforecast.model.WeatherResponse
import com.example.weatherforecast.utils.isNetworkConnectionAvailable
import com.example.weatherforecast.view.adapters.WeatherForecastRecyclerViewAdapter
import com.example.weatherforecast.viewmodel.MainViewModel
import com.example.weatherforecast.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private var weatherDetail: LiveData<WeatherResponse>? = null
    lateinit var activityMainBinding: ActivityMainBinding
    private val backgroundScope = CoroutineScope(Dispatchers.IO)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this@MainActivity,
            R.layout.activity_main)

        activityMainBinding.viewModel =
                    ViewModelProviders.of(this@MainActivity, ViewModelFactory()).get(MainViewModel::class.java)


        rv_weather_forecast.layoutManager = LinearLayoutManager(this@MainActivity)
        rv_weather_forecast.itemAnimator = DefaultItemAnimator()

        weatherDetail = (activityMainBinding.viewModel as MainViewModel).getWeatherForecast()

        if ((weatherDetail as LiveData<WeatherResponse>).value != null) {
            if (activityMainBinding.viewModel?.getWeatherForecast()?.value is WeatherResponse.Success) {
                tv_placeholder.visibility = View.GONE
                (activityMainBinding.viewModel?.getWeatherForecast()?.value as WeatherResponse.Success).result.forecast.forecastday.let {
                    rv_weather_forecast.adapter = WeatherForecastRecyclerViewAdapter(
                        this@MainActivity,
                        it
                    )
                }
            }
        }


        et_city.setOnEditorActionListener { _, actionId, event ->
            if ((event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER)) ||
                (actionId == EditorInfo.IME_ACTION_DONE)) {
                fetchForecast()
            }
            false
        }

        tv_get_forecast.setOnClickListener { fetchForecast() }

    }

    private fun setObserver(){
        (weatherDetail as LiveData<WeatherResponse>).observe(this@MainActivity,
            Observer<WeatherResponse> { t ->
                if (t is WeatherResponse.Success) {
                    tv_placeholder.visibility = View.GONE
                    rv_weather_forecast.adapter = WeatherForecastRecyclerViewAdapter(
                        this@MainActivity,
                        t.result.forecast.forecastday
                    )
                } else {
                    tv_placeholder.visibility = View.VISIBLE
                    Toast.makeText(this@MainActivity, (t as WeatherResponse.Error).message, Toast.LENGTH_SHORT).show()
                    rv_weather_forecast.adapter = WeatherForecastRecyclerViewAdapter(
                        this@MainActivity,
                        null
                    )
                }
            })
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }

    private fun fetchForecast() {
        if (this@MainActivity.isNetworkConnectionAvailable()) {
            if (et_city.text.toString().trim().isNotEmpty()) {
                pb_fetch_forecast.visibility = View.VISIBLE
                backgroundScope.launch {
                    withContext(Dispatchers.Default) {
                        (activityMainBinding.viewModel as MainViewModel).updateWeatherForecast(et_city.text.toString())
                    }
                    mainScope.launch {
                        setObserver()
                        pb_fetch_forecast.visibility = View.GONE
                    }
                }
            } else {
                Toast.makeText(this@MainActivity, "Please enter a city.", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this@MainActivity, "Please ensure network connectivity.", Toast.LENGTH_SHORT).show()
        }
    }
}
