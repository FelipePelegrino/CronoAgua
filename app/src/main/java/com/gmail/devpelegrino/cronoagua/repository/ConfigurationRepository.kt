package com.gmail.devpelegrino.cronoagua.repository

import com.gmail.devpelegrino.cronoagua.database.Configuration
import com.gmail.devpelegrino.cronoagua.database.UserProfileDatabase
import com.gmail.devpelegrino.cronoagua.database.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ConfigurationRepository(private val database: UserProfileDatabase) {

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
        var user: com.gmail.devpelegrino.cronoagua.domain.Configuration? = null
        withContext(Dispatchers.IO) {
            val aux = database.configurationDao.getConfiguration(id)
            if(aux != null) {
                user = aux?.toModel()
            }
        }
        return user
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
}