package com.gmail.devpelegrino.cronoagua.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [UserProfile::class], version = 1)
abstract class UserProfileDatabase : RoomDatabase() {
    abstract val userProfileDao: UserProfileDao
}

private lateinit var INSTANCE: UserProfileDatabase

@InternalCoroutinesApi
fun getDatabase(context: Context): UserProfileDatabase {
    synchronized(UserProfileDatabase::class.java) {
        if(!::INSTANCE.isInitialized) {
            INSTANCE = androidx.room.Room.databaseBuilder(
                context.applicationContext,
                UserProfileDatabase::class.java,
                "UserProfile"
            ).build()
        }
    }
    return INSTANCE
}