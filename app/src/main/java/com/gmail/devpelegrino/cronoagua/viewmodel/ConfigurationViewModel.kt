package com.gmail.devpelegrino.cronoagua.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.gmail.devpelegrino.cronoagua.database.UserProfileDatabase
import com.gmail.devpelegrino.cronoagua.database.getDatabase
import com.gmail.devpelegrino.cronoagua.database.toModel
import com.gmail.devpelegrino.cronoagua.domain.Configuration
import com.gmail.devpelegrino.cronoagua.domain.UserProfile
import com.gmail.devpelegrino.cronoagua.domain.toDatabase
import com.gmail.devpelegrino.cronoagua.repository.ConfigurationRepository
import kotlinx.coroutines.*

@InternalCoroutinesApi
class ConfigurationViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private lateinit var database: UserProfileDatabase
    private lateinit var configurationRepository: ConfigurationRepository
    private lateinit var configs: List<Configuration>
    private var _configuration = MutableLiveData<Configuration>()
    val configuration: LiveData<Configuration>
        get() = _configuration


    init {
        viewModelScope.launch {
            database = getDatabase(application)
            configurationRepository = ConfigurationRepository(database)
            loadConfiguration()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun changeNotify(checked: Boolean) {
        _configuration.value?.notify = checked
        updateConfig()
    }

    fun changeVibrate(checked: Boolean) {
        _configuration.value?.notifyWithVibrate = checked
        updateConfig()
    }

    fun changeWakeUpTime(wakeUp: String) {
        _configuration.value?.wakeUpTime = wakeUp
        updateConfig()
    }

    fun changeToSleepTime(toSleep: String) {
        _configuration.value?.timeToSleep = toSleep
        updateConfig()
    }

    private fun loadConfiguration() {
        viewModelScope.launch {
            configs = configurationRepository.getAllConfiguration().map {
                it.toModel()
            }
            if (configs != null && configs.isNotEmpty()) {
                _configuration.value = configurationRepository.getConfiguration(configs[0].id)
            } else {
                _configuration.value = Configuration()
            }
        }
    }

    private fun updateConfig() {
        viewModelScope.launch {
            if(_configuration.value?.id != -1) {
                _configuration?.value?.toDatabase()?.let {
                    configurationRepository.updateConfiguration(
                        it
                    )
                }
            } else {
                _configuration?.value?.toDatabase()?.let {
                    configurationRepository.insertConfiguration(
                        it
                    )
                }
            }
        }
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