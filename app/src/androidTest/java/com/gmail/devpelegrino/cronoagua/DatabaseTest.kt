package com.gmail.devpelegrino.cronoagua

import android.util.Log
import androidx.test.core.app.ApplicationProvider
import com.gmail.devpelegrino.cronoagua.database.UserProfileDao
import com.gmail.devpelegrino.cronoagua.database.getDatabase
import com.gmail.devpelegrino.cronoagua.domain.UserProfile
import com.gmail.devpelegrino.cronoagua.domain.toUserProfileDatabase
import com.gmail.devpelegrino.cronoagua.repository.UserProfileRepository
import kotlinx.coroutines.InternalCoroutinesApi
import org.junit.Test
import java.lang.Exception

class DatabaseTest {
    @InternalCoroutinesApi
    @Test
    @Throws(Exception::class)
    suspend fun writeUserAndReadInList() {
        val user: UserProfile = UserProfile(2)
        user.name = "george Database"
        val database = getDatabase(ApplicationProvider.getApplicationContext())
        val usersRepository = UserProfileRepository(database)
        usersRepository.updateUser(user.toUserProfileDatabase())
        val load = usersRepository.users.value.toString()
    }
}