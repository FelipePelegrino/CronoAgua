package com.gmail.devpelegrino.cronoagua.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.gmail.devpelegrino.cronoagua.database.*
import com.gmail.devpelegrino.cronoagua.domain.toUserProfileDatabase
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
                user = it.toUserProfileModel()
            }
        }
        return user
    }

    suspend fun updateUser(userProfile: UserProfile) {
        withContext(Dispatchers.IO) {
            database.userProfileDao.updateUserProfile(userProfile)
        }
    }

    suspend fun insertUser(userProfile: UserProfile) {
        withContext(Dispatchers.IO) {
            database.userProfileDao.insertUserProfile(userProfile)
        }
    }
}