package com.gmail.devpelegrino.cronoagua.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.gmail.devpelegrino.cronoagua.database.UserProfileDatabase
import com.gmail.devpelegrino.cronoagua.database.getDatabase
import com.gmail.devpelegrino.cronoagua.database.toModel
import com.gmail.devpelegrino.cronoagua.database.toUserProfileModel
import com.gmail.devpelegrino.cronoagua.domain.Configuration
import com.gmail.devpelegrino.cronoagua.domain.DailyDrink
import com.gmail.devpelegrino.cronoagua.domain.UserProfile
import com.gmail.devpelegrino.cronoagua.domain.toDatabase
import com.gmail.devpelegrino.cronoagua.repository.UserProfileRepository
import com.gmail.devpelegrino.cronoagua.ui.Constants
import com.gmail.devpelegrino.cronoagua.util.*
import kotlinx.coroutines.*

@InternalCoroutinesApi
class WaterManagementViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private lateinit var database: UserProfileDatabase
    private lateinit var dailyDrinkRepository: UserProfileRepository
    private var _dailyDrink = MutableLiveData<DailyDrink>()
    private var _configuration: Configuration? = null
    private var _userProfile: UserProfile? = null
    val dailyDrink: LiveData<DailyDrink>
        get() = _dailyDrink
    private var _progress = MutableLiveData<Int>()
    val progress: LiveData<Int>
        get() = _progress


    init {
        viewModelScope.launch {
            database = getDatabase(application)
            dailyDrinkRepository = UserProfileRepository(database)
            loadData()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private suspend fun loadData() {
        loadUserProfile().join()
        loadConfiguration().join()
        loadDailyDrink().join()
        _userProfile?.amountDose = calculateAmountDose(_userProfile!!, _configuration!!)
        loadProgress()
    }

    fun drink() {
        viewModelScope.launch {
            if(_dailyDrink != null && _dailyDrink.value != null) {
                if(_dailyDrink.value!!.currentAmountWater < _userProfile!!.dailyAverage) {
                    _dailyDrink.value!!.currentAmountWater += _userProfile!!.amountDose
                    _dailyDrink.value!!.lastDrinkTime = getHoursDaily()
                    dailyDrinkRepository.updateDailyDrink(_dailyDrink?.value!!.toDatabase())
                    loadDailyDrink().join()
                    loadProgress()
                }
            }
        }
    }

    private fun newDailyDrink(): com.gmail.devpelegrino.cronoagua.database.DailyDrink {
        return com.gmail.devpelegrino.cronoagua.database.DailyDrink(
            getDailyDate(),
            _userProfile?.dailyAverage!!,
            0,
            getTime(_configuration?.wakeUpTime!!),
            Constants.TIME_INTERVAL.toInt()
        )
    }

    private suspend fun loadProgress() {
        _progress.value =
            if(_dailyDrink.value != null ) {
                if (_dailyDrink.value?.totalAmountWater != 0) {
                    ((_dailyDrink.value!!.currentAmountWater.toFloat() / _dailyDrink.value!!.totalAmountWater.toFloat()) * 100).toInt()
                }
                else {
                    0
                }
            }
             else {
                0
            }
    }

    private fun loadDailyDrink(): Job  {
        return viewModelScope.launch {
            _dailyDrink.value = dailyDrinkRepository.getDailyDrink(getDailyDate())
            if (dailyDrink.value == null) {
                _dailyDrink.value = newDailyDrink().toModel()
                dailyDrinkRepository.insertDailyDrink(_dailyDrink?.value!!.toDatabase())
            }
        }
    }

    private fun loadConfiguration(): Job {
        return viewModelScope.launch {
            var configs = dailyDrinkRepository.getAllConfiguration().map {
                it.toModel()
            }
            if (configs != null && configs.isNotEmpty()) {
                _configuration = dailyDrinkRepository.getConfiguration(configs[0].id)
            }
        }
    }

    private fun loadUserProfile(): Job {
        return viewModelScope.launch {
            var users = dailyDrinkRepository.getAllUsers().map {
                it.toUserProfileModel()
            }
            if (users != null && users.isNotEmpty()) {
                _userProfile = dailyDrinkRepository.getUser(users[0].id)
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