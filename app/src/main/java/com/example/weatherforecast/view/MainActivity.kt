package com.example.weatherforecast.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.weatherforecast.R
import com.example.weatherforecast.databinding.ActivityMainBinding
import com.example.weatherforecast.model.Forecast
import com.example.weatherforecast.viewmodel.MainViewModel
import com.example.weatherforecast.viewmodel.ViewModelFactory


class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(
            this@MainActivity,
            R.layout.activity_main
        )

        activityMainBinding.viewModel =
            ViewModelProviders.of(this@MainActivity).get(MainViewModel::class.java)

        if (savedInstanceState == null) {
            activityMainBinding.flFragmentParent.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction().add(
                R.id.fl_fragment_parent,
                LoginFragment()
            ).commit()
        }

    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
