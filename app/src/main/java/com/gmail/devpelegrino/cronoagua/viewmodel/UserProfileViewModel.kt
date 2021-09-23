package com.gmail.devpelegrino.cronoagua.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserProfileViewModel : ViewModel() {

    private val _navigateToWaterManager = MutableLiveData<Boolean>()
    val navigateToWaterManager: LiveData<Boolean>
        get() = _navigateToWaterManager

    fun onSaveClicked() {
        _navigateToWaterManager.value = true
    }

    fun onNavigatedToWaterManager() {
        _navigateToWaterManager.value = false
    }

    override fun onCleared() {
        super.onCleared()
    }
}