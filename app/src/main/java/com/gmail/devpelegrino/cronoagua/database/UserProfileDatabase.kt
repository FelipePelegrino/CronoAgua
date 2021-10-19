package com.gmail.devpelegrino.cronoagua.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import androidx.sqlite.db.SupportSQLiteDatabase

import androidx.room.migration.Migration


@Database(
    version = 2,
    entities = [UserProfile::class, Configuration::class]
)
abstract class UserProfileDatabase : RoomDatabase() {
    abstract val userProfileDao: UserProfileDao
    abstract val configurationDao: ConfigurationDao
}

private lateinit var INSTANCE: UserProfileDatabase

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE Configuration (user_id INTEGER NOT NULL," +
                    " notify INTEGER NOT NULL," +
                    " notify_with_vibrate INTEGER NOT NULL," +
                    " wake_up_time TEXT NOT NULL," +
                    " time_to_sleep TEXT NOT NULL," +
                    " PRIMARY KEY(user_id))"
        )
    }
}

@InternalCoroutinesApi
fun getDatabase(context: Context): UserProfileDatabase {
    synchronized(UserProfileDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = androidx.room.Room.databaseBuilder(
                context.applicationContext,
                UserProfileDatabase::class.java,
                "UserProfile"
            )
                .addMigrations(MIGRATION_1_2)
                .build()
        }
    }
    return INSTANCE
}