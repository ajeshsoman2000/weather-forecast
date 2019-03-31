package com.example.weatherforecast.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders

import com.example.weatherforecast.R
import com.example.weatherforecast.utils.changeDateFormat
import com.example.weatherforecast.utils.getDayFromDate
import com.example.weatherforecast.viewmodel.MainViewModel
import com.example.weatherforecast.viewmodel.ViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_weather_detail.*

class WeatherDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_detail, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProviders.of(activity as MainActivity).get(MainViewModel::class.java)
        tv_condition.text = "Expect: ${viewModel.selectedForecast.day.condition.text}"
        tv_max_temp.text = "Max: ${viewModel.selectedForecast.day.maxtemp_c}\u2103 / ${viewModel.selectedForecast.day.maxtemp_f}\u2109"
        tv_min_temp.text = "Min: ${viewModel.selectedForecast.day.mintemp_c}\u2103 / ${viewModel.selectedForecast.day.mintemp_f}℉"
        tv_windspeed.text = "Max: ${viewModel.selectedForecast.day.maxwind_kph} kmph / ${viewModel.selectedForecast.day.maxwind_mph} mph"
        tv_visibility.text = "Avg: ${viewModel.selectedForecast.day.avgvis_km} km / ${viewModel.selectedForecast.day.avgvis_miles} miles"
        tv_humidity.text = "Avg: ${viewModel.selectedForecast.day.avghumidity} %"
        Picasso.with(context).load("https:${viewModel
            ?.selectedForecast?.day?.condition?.icon}").into(iv_condition)
        tv_day.text = "${viewModel.selectedForecast.date.changeDateFormat("yyyy-MM-dd", "dd-MM-yyyy").getDayFromDate("dd-MM-yyyy")}: " +
                "${viewModel.selectedForecast.date.changeDateFormat("yyyy-MM-dd", "dd-MM-yyyy")}"
    }

}
