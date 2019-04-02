package com.example.weatherforecast.view

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecast.R
import com.example.weatherforecast.model.Forecast
import com.example.weatherforecast.model.WeatherResponse
import com.example.weatherforecast.repository.database.userdetail.UserDetailEntity
import com.example.weatherforecast.utils.isNetworkConnectionAvailable
import com.example.weatherforecast.view.adapters.WeatherForecastRecyclerViewAdapter
import com.example.weatherforecast.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_forecast_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.android.synthetic.main.custom_progressbar.*

class ForecastListFragment: Fragment() {

    lateinit var mainViewModel: MainViewModel
    private val TAG = "ForecastListFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProviders.of(activity as MainActivity).get(MainViewModel::class.java)
        if (mainViewModel.loggedInUser != null) {
            (activity as MainActivity).supportActionBar?.title =
                "Welcome ${(mainViewModel.loggedInUser as UserDetailEntity).name}"
        }
    }

    override fun onDestroy() {
        (activity as MainActivity).supportActionBar?.title = getString(R.string.app_name)
        super.onDestroy()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_forecast_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_weather_forecast.layoutManager = LinearLayoutManager(activity)
        rv_weather_forecast.itemAnimator = DefaultItemAnimator()

        rv_weather_forecast.adapter = WeatherForecastRecyclerViewAdapter(activity as MainActivity,
            mainViewModel.weatherForecastlist)

        setObserver()

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
        mainViewModel.getWeatherForecast().observe(activity as MainActivity,
            Observer<WeatherResponse> { t ->
                if (t is WeatherResponse.Success) {
                    tv_placeholder.visibility = View.GONE
                    rv_weather_forecast.adapter?.notifyDataSetChanged()
                } else {
                    tv_placeholder.visibility = View.VISIBLE
                    Toast.makeText(activity as MainActivity, (t as WeatherResponse.Error).message,
                        Toast.LENGTH_SHORT).show()
                    rv_weather_forecast.adapter?.notifyDataSetChanged()
                }
            })
    }
    private fun fetchForecast() {
        val imm = (activity as MainActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
        pb_fetch_forecast.visibility = View.VISIBLE
        mainViewModel.backgroundScope.launch {
            withContext(Dispatchers.Default) {
                mainViewModel.updateWeatherForecast(et_city.text.toString())
            }
            mainViewModel.mainScope.launch {
                pb_fetch_forecast.visibility = View.GONE
                rv_weather_forecast.adapter?.notifyDataSetChanged()
            }
        }
    }
}