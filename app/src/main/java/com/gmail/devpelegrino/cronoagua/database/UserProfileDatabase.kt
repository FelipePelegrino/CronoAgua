package com.gmail.devpelegrino.cronoagua.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import androidx.sqlite.db.SupportSQLiteDatabase

import androidx.room.migration.Migration


@Database(
    version = 3,
    entities = [UserProfile::class, Configuration::class, DailyDrink::class]
)
abstract class UserProfileDatabase : RoomDatabase() {
    abstract val userProfileDao: UserProfileDao
    abstract val configurationDao: ConfigurationDao
    abstract val dailyDrinkDao: DailyDrinkDao
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

val MIGRATION_2_3: Migration = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE DailyDrink (date TEXT NOT NULL," +
                    " total_amount_water INTEGER NOT NULL," +
                    " current_amount_water INTEGER NOT NULL," +
                    " last_drink_time TEXT NOT NULL," +
                    " time_interval INTEGER NOT NULL," +
                    " PRIMARY KEY(date))"
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
                .addMigrations(MIGRATION_2_3)
                .build()
        }
    }
    return INSTANCE
}