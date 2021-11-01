package com.gmail.devpelegrino.cronoagua.database

import androidx.room.*
import java.time.LocalDate

@Dao
interface DailyDrinkDao {

    @Query("SELECT * FROM DailyDrink WHERE date(date) = date(:date)")
    fun getDailyDrink(date: LocalDate): DailyDrink

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDailyDrink(dailyDrink: DailyDrink)

    @Update
    fun updateDailyDrink(dailyDrink: DailyDrink)
}