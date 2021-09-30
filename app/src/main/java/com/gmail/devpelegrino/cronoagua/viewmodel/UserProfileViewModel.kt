package com.gmail.devpelegrino.cronoagua.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.gmail.devpelegrino.cronoagua.database.UserProfileDatabase
import com.gmail.devpelegrino.cronoagua.database.getDatabase
import com.gmail.devpelegrino.cronoagua.domain.Climate
import com.gmail.devpelegrino.cronoagua.domain.UserProfile
import com.gmail.devpelegrino.cronoagua.domain.toUserProfileDatabase
import com.gmail.devpelegrino.cronoagua.repository.UserProfileRepository
import kotlinx.coroutines.*

@OptIn(InternalCoroutinesApi::class)
class UserProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private lateinit var database : UserProfileDatabase
    private lateinit var usersRepository : UserProfileRepository

    private lateinit var users : LiveData<List<UserProfile>>

    private var _userProfile : MutableLiveData<UserProfile> = MutableLiveData()

    val userProfile: LiveData<UserProfile>
        get() = _userProfile

    private val _navigateToWaterManager = MutableLiveData<Boolean>()

    val navigateToWaterManager: LiveData<Boolean>
        get() = _navigateToWaterManager

    init {
        viewModelScope.launch {
            database = getDatabase(application)
            usersRepository = UserProfileRepository(database)
            Log.i("TesteRepository", usersRepository.users.toString())
            Log.i("TesteRepository", usersRepository.users.value.toString())
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
        //TODO: calcular quantidade de água
        viewModelScope.launch {
            if(_userProfile != null && _userProfile.value != null) {
                if(_userProfile!!.value!!.id == -1) {
                    usersRepository.insertUser(_userProfile.value!!.toUserProfileDatabase())
                } else{
                    usersRepository.updateUser(_userProfile.value!!.toUserProfileDatabase())
                }
            }
        }
    }

    fun loadUserProfile() {
        viewModelScope.launch {
            users = usersRepository.users
            if(users != null && users.value != null) {
                loadUser()
            } else {
                _userProfile.value = UserProfile()
            }
        }
    }

    private fun loadUser() {
        if(users != null && users.value != null && users.value!![0] != null) {
            viewModelScope.launch {
                _userProfile.value = usersRepository.getUser(users.value!![0].id)
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

    //TODO: criar databinding adapters
}