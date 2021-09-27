package com.gmail.devpelegrino.cronoagua.repository

import androidx.lifecycle.LiveData
import com.gmail.devpelegrino.cronoagua.database.UserProfile
import com.gmail.devpelegrino.cronoagua.database.UserProfileDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserProfileRepository(private val database: UserProfileDatabase) {

    private lateinit var _users : LiveData<List<UserProfile>>
    private lateinit var _user : LiveData<UserProfile>


    suspend fun getAllUsers() : LiveData<List<UserProfile>> {
        withContext(Dispatchers.IO) {
            _users = database.userProfileDao.getAllUserProfile()
        }
        return _users
    }

    suspend fun getUser(id: Int) : LiveData<UserProfile> {
        withContext(Dispatchers.IO) {
            _user = database.userProfileDao.getUserProfile(id)
        }
        return _user
    }
}