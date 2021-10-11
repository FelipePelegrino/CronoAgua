package com.gmail.devpelegrino.cronoagua.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.gmail.devpelegrino.cronoagua.R
import com.gmail.devpelegrino.cronoagua.util.AppStartManagement
import kotlinx.coroutines.InternalCoroutinesApi

class MainActivity : AppCompatActivity() {
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppStartManagement.startApp(application, supportFragmentManager)
    }
}