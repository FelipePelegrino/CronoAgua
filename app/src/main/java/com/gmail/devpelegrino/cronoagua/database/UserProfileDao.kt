package com.gmail.devpelegrino.cronoagua.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserProfileDao {

    @Query("SELECT user_id, name, age, weight, is_practice_exercise, local_climate, amount_dose, daily_average FROM UserProfile WHERE user_id = :id")
    fun getUserProfile(id: Int): UserProfile?

    @Query("select * from userprofile")
    fun getAllUserProfile(): List<UserProfile>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserProfile(userProfile: UserProfile)

    @Update
    fun updateUserProfile(userProfile: UserProfile)
}
