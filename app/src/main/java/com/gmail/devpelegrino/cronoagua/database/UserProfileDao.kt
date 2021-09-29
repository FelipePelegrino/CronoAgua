package com.gmail.devpelegrino.cronoagua.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface UserProfileDao {

    @Query("SELECT * FROM UserProfile WHERE user_id = :id")
    fun getUserProfile(id: Int): MutableLiveData<UserProfile>

    @Query("SELECT * FROM UserProfile")
    fun getAllUserProfile(): LiveData<List<UserProfile>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserProfile(userProfile: UserProfile)

    @Update
    fun updateUserProfile(userProfile: UserProfile)
}
