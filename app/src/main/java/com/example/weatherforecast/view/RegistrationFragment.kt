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
import com.example.weatherforecast.utils.isValidEmail
import com.example.weatherforecast.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.custom_progressbar.*
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationFragment: Fragment() {

    lateinit var mainViewModel: MainViewModel
    private val TAG = "RegistrationFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_registration, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProviders.of(activity as MainActivity).get(MainViewModel::class.java)

        btn_register.setOnClickListener {
            addUser()
        }

        et_register_password.setOnEditorActionListener { _, actionId, event ->
            if ((event != null && (event.keyCode == KeyEvent.KEYCODE_ENTER)) ||
                (actionId == EditorInfo.IME_ACTION_DONE)) {
                addUser()
            }
            false
        }
    }

    private fun addUser() {
        val name = et_register_name.text.toString().trim()
        val email = et_register_email.text.toString().trim()
        val password = et_register_password.text.toString().trim()
        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            if (email.isValidEmail()) {
                val imm =
                    (activity as MainActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
                pb_fetch_forecast.visibility = View.VISIBLE
                mainViewModel.backgroundScope.launch {
                    withContext(Dispatchers.Default) {
                        mainViewModel.registerUser(email, password, name)
                    }
                    mainViewModel.mainScope.launch {
                        pb_fetch_forecast.visibility = View.GONE
                        (activity as MainActivity).supportFragmentManager.popBackStack()
                    }
                }
            } else {
                Toast.makeText(activity as MainActivity, "Invalid email format.", Toast.LENGTH_LONG).show()
            }

        } else {
            Toast.makeText(activity, "All fields are mandatory.", Toast.LENGTH_LONG).show()
        }
    }
}