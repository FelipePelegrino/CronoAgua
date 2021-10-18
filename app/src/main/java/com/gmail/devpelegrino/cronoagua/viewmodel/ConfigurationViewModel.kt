package com.gmail.devpelegrino.cronoagua.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmail.devpelegrino.cronoagua.domain.Configuration

class ConfigurationViewModel(application: Application) : AndroidViewModel(application) {

    private var _configuration = MutableLiveData<Configuration>()
    val configuration : LiveData<Configuration>
        get() = _configuration

}