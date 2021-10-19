package com.gmail.devpelegrino.cronoagua.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.gmail.devpelegrino.cronoagua.R
import com.gmail.devpelegrino.cronoagua.domain.Configuration

class ConfigurationViewModel(application: Application) : AndroidViewModel(application) {

    private var _configuration = MutableLiveData<Configuration>()
    val configuration : LiveData<Configuration>
        get() = _configuration

//    private var _onUpdate = MutableLiveData<Boolean>()
//    val onUpdate : LiveData<Boolean>
//        get() = _onUpdate

    init {
        _configuration.value = Configuration()
    }

    fun changeNotify(context: Context, checked : Boolean) {
        Toast.makeText(context, "notify", Toast.LENGTH_SHORT).show()
    }

    fun changeVibrate(context: Context, checked : Boolean) {
        Toast.makeText(context, "vibrate", Toast.LENGTH_SHORT).show()
    }

    fun changeWakeUpTime(context: Context, data: String) {
        Toast.makeText(context, data, Toast.LENGTH_SHORT).show()
    }

    fun changeToSleepTime(context: Context, data: String) {
        Toast.makeText(context, data, Toast.LENGTH_SHORT).show()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ConfigurationViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ConfigurationViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}