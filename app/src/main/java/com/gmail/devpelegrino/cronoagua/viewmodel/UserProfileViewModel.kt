package com.gmail.devpelegrino.cronoagua.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.gmail.devpelegrino.cronoagua.database.UserProfileDatabase
import com.gmail.devpelegrino.cronoagua.database.getDatabase
import com.gmail.devpelegrino.cronoagua.domain.Climate
import com.gmail.devpelegrino.cronoagua.domain.UserProfile
import com.gmail.devpelegrino.cronoagua.repository.UserProfileRepository
import kotlinx.coroutines.*

@OptIn(InternalCoroutinesApi::class)
class UserProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private lateinit var database : UserProfileDatabase
    private lateinit var usersRepository : UserProfileRepository
    private lateinit var users : LiveData<List<com.gmail.devpelegrino.cronoagua.database.UserProfile>>

    private lateinit var _userProfile : MutableLiveData<com.gmail.devpelegrino.cronoagua.database.UserProfile>

    val userProfile: LiveData<com.gmail.devpelegrino.cronoagua.database.UserProfile>
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
        _userProfile.value?.local_climate = climate
        _userProfile.value?.is_practice_exercise = isPracticeExercise
//        database.userProfileDao.updateUserProfile(_userProfile.value)
        //TODO: configuração climate/isPracticeExercise
    }

    fun loadUserProfile() {
        //TODO: recuperar dados do banco
        viewModelScope.launch {
            users = usersRepository.getAllUsers()
            Log.i("Teste", usersRepository.getAllUsers().value.toString())
            if(users != null && users.value != null) {
                loadUser()
            }
        }
    }

    private fun loadUser() {
        if(users != null && users.value != null && users.value!![0] != null) {
            viewModelScope.launch {
                _userProfile = usersRepository.getUser(users.value!![0].user_id)
            }

//            _userProfile.value?.name = users.value!![0].name
//            _userProfile.value?.age = users.value!![0].age
//            _userProfile.value?.weight = users.value!![0].weight
//            _userProfile.value?.localClimate = users.value!![0].local_climate
//            _userProfile.value?.isPracticeExercise = users.value!![0].is_practice_exercise
//            _userProfile.value?.dailyAverage = users.value!![0].daily_average
//            _userProfile.value?.amountDose = users.value!![0].amount_dose
        }
    }

    //TODO: criar databinding adapters

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