package com.gmail.devpelegrino.cronoagua.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import androidx.sqlite.db.SupportSQLiteDatabase

import androidx.room.migration.Migration


@Database(
    version = 4,
    entities = [UserProfile::class, Configuration::class, DailyDrink::class]
)
@TypeConverters(TypeConverters::class)
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

val MIGRATION_3_4: Migration = object: Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        val createNewTable = "CREATE TABLE DailyDrinkNew (date TEXT NOT NULL," +
                " total_amount_water INTEGER NOT NULL," +
                " current_amount_water INTEGER NOT NULL," +
                " last_drink_time TEXT NOT NULL," +
                " time_interval TEXT NOT NULL," +
                " PRIMARY KEY(date))"
        val copyData = "INSERT INTO DailyDrinkNew (date, total_amount_water, current_amount_water, last_drink_time, time_interval) " +
                "SELECT date, total_amount_water, current_amount_water, last_drink_time, time_interval FROM DailyDrink"
        val dropOldTable = "DROP TABLE DailyDrink"
        val changeNameNewTable = "ALTER TABLE DailyDrinkNew RENAME TO DailyDrink"

        database.execSQL(createNewTable)
        database.execSQL(copyData)
        database.execSQL(dropOldTable)
        database.execSQL(changeNameNewTable)
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
                .addMigrations(MIGRATION_3_4)
                .build()
        }
    }
    return INSTANCE
}