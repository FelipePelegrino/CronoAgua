package com.gmail.devpelegrino.cronoagua.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.gmail.devpelegrino.cronoagua.database.UserProfile
import com.gmail.devpelegrino.cronoagua.database.UserProfileDatabase
import com.gmail.devpelegrino.cronoagua.database.asDomainModel
import com.gmail.devpelegrino.cronoagua.database.toUserProfileDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserProfileRepository(private val database: UserProfileDatabase) {

    val users : LiveData<List<com.gmail.devpelegrino.cronoagua.domain.UserProfile>> = Transformations.map(database.userProfileDao.getAllUserProfile()) {
        it.asDomainModel()
    }

    suspend fun getUser(id: Int) : com.gmail.devpelegrino.cronoagua.domain.UserProfile? {
        var user: com.gmail.devpelegrino.cronoagua.domain.UserProfile? = null
        withContext(Dispatchers.IO) {
            Transformations.map(database.userProfileDao.getUserProfile(id)) {
                user = it.toUserProfileDomain()
            }
        }
        return user
    }
}