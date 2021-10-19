package com.gmail.devpelegrino.cronoagua.database

import androidx.room.*

@Dao
interface ConfigurationDao {

    @Query("SELECT * FROM Configuration WHERE user_id = :id")
    fun getConfiguration(id: Int) : Configuration?

    @Query("SELECT * FROM Configuration")
    fun getAllConfiguration() : List<Configuration>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertConfiguration(configuration: Configuration)

    @Update
    fun updateConfiguration(configuration: Configuration)
}
