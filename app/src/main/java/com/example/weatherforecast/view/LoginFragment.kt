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
import androidx.lifecycle.ViewModelProviders
import com.example.weatherforecast.R
import com.example.weatherforecast.model.LoginResponse
import com.example.weatherforecast.utils.isValidEmail
import com.example.weatherforecast.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.custom_progressbar.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.launch

class LoginFragment: Fragment() {

    lateinit var mainViewModel: MainViewModel
    private val TAG = "LoginFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProviders.of(activity as MainActivity).get(MainViewModel::class.java)

        btn_login.setOnClickListener {
            authenticateUser()
        }

        et_login_password.setOnEditorActionListener { _, actionId, event ->
            if ((event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER)) ||
                (actionId == EditorInfo.IME_ACTION_DONE)) {
                authenticateUser()
            }
            false
        }

        btn_register.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction().addToBackStack(null).replace(
                R.id.fl_fragment_parent,
                RegistrationFragment()).commit()
        }

    }

    private fun authenticateUser() {
        mainViewModel.backgroundScope.launch {
            val email = et_login_email.text.toString().trim()
            val password = et_login_password.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {

                if (email.isValidEmail()) {
                    val imm =
                        (activity as MainActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
                    mainViewModel.mainScope.launch { pb_fetch_forecast.visibility = View.VISIBLE }
                    val loginResponse = mainViewModel.authenticateUser(email, password)

                    processResponse(loginResponse)
                } else {
                    mainViewModel.mainScope.launch {
                        Toast.makeText(activity as MainActivity, "Invalid email format.", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                mainViewModel.mainScope.launch {
                    Toast.makeText(activity as MainActivity, "All fields are mandatory.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun processResponse(loginResponse: LoginResponse) {
        when(loginResponse) {
            is LoginResponse.Success -> {
                mainViewModel.mainScope.launch { pb_fetch_forecast.visibility = View.GONE }
                mainViewModel.loggedInUser = loginResponse.authenticatedUser

                (activity as MainActivity).supportFragmentManager.beginTransaction()
                    .remove(this@LoginFragment).commit()
                (activity as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.fl_fragment_parent,
                        ForecastListFragment()
                    ).commit()
            }

            is LoginResponse.Error -> {
                mainViewModel.mainScope.launch {
                    pb_fetch_forecast.visibility = View.GONE
                    Toast.makeText(activity as MainActivity, loginResponse.errorMsg, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}