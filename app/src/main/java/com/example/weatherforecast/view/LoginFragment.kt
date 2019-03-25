package com.example.weatherforecast.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.weatherforecast.R
import com.example.weatherforecast.repository.database.userdetail.UserDetailDB
import com.example.weatherforecast.repository.database.userdetail.UserDetailDao
import com.example.weatherforecast.utils.hashPassword
import com.example.weatherforecast.viewmodel.MainViewModel
import com.example.weatherforecast.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.launch

class LoginFragment: Fragment() {

    private var db: UserDetailDB? = null
    private var userDao: UserDetailDao? = null
    lateinit var mainViewModel: MainViewModel
    private val TAG = "LoginFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_login, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProviders.of(activity as MainActivity, ViewModelFactory()).get(MainViewModel::class.java)
        db = UserDetailDB.getAppDataBase(context = activity as MainActivity)
        userDao = db?.userDetailDao()

        val email = et_login_email.toString().trim()
        val password = et_login_password.toString().trim().hashPassword()

        btn_login.setOnClickListener {
            mainViewModel.backgroundScope.launch {
                val userDet = userDao?.getUser(email)
                Log.v(TAG, "fetched user = ${userDet.toString()}")
            }
        }

        btn_register.setOnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction().addToBackStack(null).replace(
                R.id.fl_fragment_parent,
                RegistrationFragment()).commit()
        }

    }
}