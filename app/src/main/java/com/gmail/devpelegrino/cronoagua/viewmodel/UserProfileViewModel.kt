package com.gmail.devpelegrino.cronoagua.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.devpelegrino.cronoagua.domain.UserProfile

class UserProfileViewModel : ViewModel() {

    private val _userProfile = MutableLiveData<UserProfile>()
    val userProfile: LiveData<UserProfile>
        get() = _userProfile

    private val _navigateToWaterManager = MutableLiveData<Boolean>()
    val navigateToWaterManager: LiveData<Boolean>
        get() = _navigateToWaterManager

    fun onSaveClicked() {
        _navigateToWaterManager.value = true
    }

    fun onNavigatedToWaterManager() {
        _navigateToWaterManager.value = false
    }

    fun saveUserProfile(name: String, age: Int, weight: Float) {
        _userProfile.value?.name = name
        _userProfile.value?.age = age
        _userProfile.value?.weight = weight
    }

    override fun onCleared() {
        super.onCleared()
    }
}