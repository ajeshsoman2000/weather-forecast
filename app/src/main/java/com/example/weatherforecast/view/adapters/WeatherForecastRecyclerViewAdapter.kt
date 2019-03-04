package com.example.weatherforecast.view.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecast.R
import com.example.weatherforecast.model.Forecast
import android.view.LayoutInflater
import com.example.weatherforecast.utils.changeDateFormat
import com.example.weatherforecast.view.MainActivity
import com.example.weatherforecast.view.WeatherDetailFragment
import com.squareup.picasso.Picasso


class WeatherForecastRecyclerViewAdapter(val context: Context, val weatherForecastList: List<Forecast>?): RecyclerView.Adapter<WeatherForecastItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherForecastItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_forecast_list_item, parent, false)
        return WeatherForecastItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return weatherForecastList?.size ?: 0
    }

    override fun onBindViewHolder(holder: WeatherForecastItemViewHolder, position: Int) {
        val forecast = weatherForecastList?.let {
            it[position]
        }
        holder.tvDate.text = forecast?.date?.changeDateFormat("yyyy-MM-dd", "dd-MM-yyyy")
        holder.tvMaxTemp.text = "Max: ${forecast?.day?.maxtemp_c}\u2103"
        holder.tvMinTemp.text = "Min: ${forecast?.day?.mintemp_c}\u2103"
        holder.tvCondition.text = forecast?.day?.condition?.text
        Picasso.with(context).load("https:${forecast?.day?.condition?.icon}").into(holder.ivCondtion)
        holder.itemView.setOnClickListener {
            (context as MainActivity).apply {
                activityMainBinding.viewModel?.selectedForecast = forecast as Forecast
                activityMainBinding.flFragmentParent.visibility = View.VISIBLE
                supportFragmentManager.beginTransaction().addToBackStack(null).add(R.id.fl_fragment_parent,
                    WeatherDetailFragment()).commit()
            }
        }
    }
}

class WeatherForecastItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var tvDate: TextView = view.findViewById(R.id.tv_date)
    var tvMaxTemp: TextView = view.findViewById(R.id.tv_max_temp)
    var tvMinTemp: TextView = view.findViewById(R.id.tv_min_temp)
    var tvCondition: TextView = view.findViewById(R.id.tv_condition)
    var ivCondtion: ImageView = view.findViewById(R.id.iv_condition)

}