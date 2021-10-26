package com.gmail.devpelegrino.cronoagua.repository

import com.gmail.devpelegrino.cronoagua.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserProfileRepository(private val database: UserProfileDatabase) {

    //Functions access UserProfile table
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

    //Functions access Configuration table
    suspend fun getAllConfiguration() : List<com.gmail.devpelegrino.cronoagua.database.Configuration> {
        var configs: List<com.gmail.devpelegrino.cronoagua.database.Configuration> = listOf()
        withContext(Dispatchers.IO) {
            val aux = database.configurationDao.getAllConfiguration()
            if(aux != null) {
                configs = aux
            }
        }
        return configs
    }

    suspend fun getConfiguration(id: Int) : com.gmail.devpelegrino.cronoagua.domain.Configuration? {
        var config: com.gmail.devpelegrino.cronoagua.domain.Configuration? = null
        withContext(Dispatchers.IO) {
            val aux = database.configurationDao.getConfiguration(id)
            if(aux != null) {
                config = aux?.toModel()
            }
        }
        return config
    }

    suspend fun updateConfiguration(config: Configuration) {
        withContext(Dispatchers.IO) {
            database.configurationDao.updateConfiguration(config)
        }
    }

    suspend fun insertConfiguration(config: Configuration) {
        withContext(Dispatchers.IO) {
            database.configurationDao.insertConfiguration(config)
        }
    }

    //Functions access DailyDrink table
    suspend fun getDailyDrink(date: String) : com.gmail.devpelegrino.cronoagua.domain.DailyDrink? {
        var dailyDrink: com.gmail.devpelegrino.cronoagua.domain.DailyDrink? = null
        withContext(Dispatchers.IO) {
            val aux = database.dailyDrinkDao.getDailyDrink(date)
            if(aux != null) {
                dailyDrink = aux?.toModel()
            }
        }
        return dailyDrink
    }

    suspend fun updateDailyDrink(dailyDrink: DailyDrink) {
        withContext(Dispatchers.IO) {
            database.dailyDrinkDao.updateDailyDrink(dailyDrink)
        }
    }

    suspend fun insertDailyDrink(dailyDrink: DailyDrink) {
        withContext(Dispatchers.IO) {
            database.dailyDrinkDao.insertDailyDrink(dailyDrink)
        }
    }
}