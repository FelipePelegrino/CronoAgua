package com.gmail.devpelegrino.cronoagua.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.gmail.devpelegrino.cronoagua.database.UserProfileDatabase
import com.gmail.devpelegrino.cronoagua.database.getDatabase
import com.gmail.devpelegrino.cronoagua.database.toUserProfileModel
import com.gmail.devpelegrino.cronoagua.domain.*
import com.gmail.devpelegrino.cronoagua.repository.UserProfileRepository
import com.gmail.devpelegrino.cronoagua.util.calculateDailyAverage
import com.gmail.devpelegrino.cronoagua.util.getDailyDate
import kotlinx.coroutines.*

@OptIn(InternalCoroutinesApi::class)
class UserProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private lateinit var database : UserProfileDatabase
    private lateinit var usersRepository : UserProfileRepository

    private lateinit var users : List<UserProfile>

    private var _userProfile : MutableLiveData<UserProfile> = MutableLiveData()
    private var _dailyDrink : DailyDrink? = null

    val userProfile: LiveData<UserProfile>
        get() = _userProfile

    private val _navigateToWaterManager = MutableLiveData<Boolean>()

    val navigateToWaterManager: LiveData<Boolean>
        get() = _navigateToWaterManager

    init {
        viewModelScope.launch {
            database = getDatabase(application)
            usersRepository = UserProfileRepository(database)
            loadUserProfile()
        }
    }

    fun onSaveClicked() {
        _navigateToWaterManager.value = true
    }

    fun onNavigatedToWaterManager() {
        _navigateToWaterManager.value = false
    }

    fun saveUserProfile(name: String, age: Int, weight: Float, climate: Climate, isPracticeExercise: Boolean) {
        _userProfile.value?.name = name
        _userProfile.value?.age = age
        _userProfile.value?.weight = weight
        _userProfile.value?.localClimate = climate
        _userProfile.value?.isPracticeExercise = isPracticeExercise
        _userProfile.value?.dailyAverage = calculateDailyAverage(_userProfile.value!!)
        viewModelScope.launch {
            if(_userProfile != null && _userProfile.value != null) {
                if(_userProfile!!.value!!.id == -1) {
                    _userProfile!!.value!!.id = 1
                    usersRepository.insertUser(_userProfile.value!!.toUserProfileDatabase())
                } else{
                    usersRepository.updateUser(_userProfile.value!!.toUserProfileDatabase())
                    _dailyDrink = usersRepository.getDailyDrink(getDailyDate())
                    if(_dailyDrink != null) {
                        _dailyDrink!!.totalAmountWater = _userProfile?.value!!.dailyAverage
                        usersRepository.updateDailyDrink(_dailyDrink?.toDatabase()!!)
                    }
                }
            }
        }
    }

    fun loadUserProfile() {
        viewModelScope.launch {
            users = usersRepository.getAllUsers().map {
                it.toUserProfileModel()
            }
            if(users != null && users.isNotEmpty()) {
                loadUser()
            } else {
                _userProfile.value = UserProfile()
            }
        }
    }

    private fun loadUser() {
        if(users.isNotEmpty()) {
            viewModelScope.launch {
                _userProfile.value = usersRepository.getUser(users[0].id)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(UserProfileViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return UserProfileViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}