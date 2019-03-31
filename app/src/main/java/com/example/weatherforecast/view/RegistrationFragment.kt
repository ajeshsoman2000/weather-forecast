package com.example.weatherforecast.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.weatherforecast.R
import com.example.weatherforecast.repository.database.userdetail.UserDetailDB
import com.example.weatherforecast.repository.database.userdetail.UserDetailDao
import com.example.weatherforecast.repository.database.userdetail.UserDetailEntity
import com.example.weatherforecast.utils.hashPassword
import com.example.weatherforecast.viewmodel.MainViewModel
import com.example.weatherforecast.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.custom_progressbar.*
import kotlinx.android.synthetic.main.fragment_registration.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationFragment: Fragment() {

    private var db: UserDetailDB? = null
    private var userDao: UserDetailDao? = null
    lateinit var mainViewModel: MainViewModel
    private val TAG = "RegistrationFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_registration, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProviders.of(activity as MainActivity).get(MainViewModel::class.java)
        db = UserDetailDB.getAppDataBase(context = activity as MainActivity)
        userDao = db?.userDetailDao()

        btn_register.setOnClickListener {
            addUser()
        }
    }

    private fun addUser() {
        val name = et_register_name.text.toString().trim()
        val email = et_register_email.text.toString().trim()
        val password = et_register_password.text.toString().trim()
        if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
            val imm = (activity as MainActivity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
            pb_fetch_forecast.visibility = View.VISIBLE
            mainViewModel.backgroundScope.launch {
                withContext(Dispatchers.Default) {
                    userDao?.addUser(UserDetailEntity(email, name, password.hashPassword()))
                }
                mainViewModel.mainScope.launch {
                    pb_fetch_forecast.visibility = View.GONE
                    (activity as MainActivity).supportFragmentManager.popBackStack()
                }
            }

        } else {
            Toast.makeText(activity, "All fields are mandatory.", Toast.LENGTH_LONG).show()
        }
    }
}