package com.gmail.devpelegrino.cronoagua.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.gmail.devpelegrino.cronoagua.database.UserProfileDatabase
import com.gmail.devpelegrino.cronoagua.database.getDatabase
import com.gmail.devpelegrino.cronoagua.database.toModel
import com.gmail.devpelegrino.cronoagua.domain.Configuration
import com.gmail.devpelegrino.cronoagua.repository.ConfigurationRepository
import kotlinx.coroutines.*

@InternalCoroutinesApi
class WaterManagementViewModel(application: Application) : AndroidViewModel(application) {

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

    private fun loadConfiguration() {
        viewModelScope.launch {
            configs = configurationRepository.getAllConfiguration().map {
                it.toModel()
            }
            if (configs != null && configs.isNotEmpty()) {
                _configuration.value = configurationRepository.getConfiguration(configs[0].id)
            }
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WaterManagementViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return WaterManagementViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}