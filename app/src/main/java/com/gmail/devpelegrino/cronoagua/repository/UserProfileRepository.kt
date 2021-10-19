package com.gmail.devpelegrino.cronoagua.repository

import com.gmail.devpelegrino.cronoagua.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserProfileRepository(private val database: UserProfileDatabase) {

    suspend fun getAllUsers() : List<com.gmail.devpelegrino.cronoagua.database.UserProfile> {
        var users: List<com.gmail.devpelegrino.cronoagua.database.UserProfile> = listOf()
        withContext(Dispatchers.IO) {
            val aux = database.userProfileDao.getAllUserProfile()
            if(aux != null) {
                users = database.userProfileDao.getAllUserProfile()
            }
        }
        return users
    }

    suspend fun getUser(id: Int) : com.gmail.devpelegrino.cronoagua.domain.UserProfile? {
        var user: com.gmail.devpelegrino.cronoagua.domain.UserProfile? = null
        withContext(Dispatchers.IO) {
            val aux = database.userProfileDao.getUserProfile(id)
            if(aux != null) {
                user = database.userProfileDao.getUserProfile(id)!!.toUserProfileModel()
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