package com.gmail.devpelegrino.cronoagua.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.devpelegrino.cronoagua.domain.Climate

@Entity
data class UserProfile constructor(
    @PrimaryKey
    var user_id: Int,
    var name: String,
    var weight: Float,
    var age: Int,
    var is_practice_exercise: Boolean,
    var local_climate: Climate,
    var daily_average: Int,
    var amount_dose: Int
)