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

fun List<UserProfile>.asDomainModel(): List<com.gmail.devpelegrino.cronoagua.domain.UserProfile> {
    return map {
        com.gmail.devpelegrino.cronoagua.domain.UserProfile(
            id = it.user_id,
            name = it.name,
            weight = it.weight,
            age = it.age,
            isPracticeExercise =  it.is_practice_exercise,
            localClimate =  it.local_climate,
            dailyAverage = it.daily_average,
            amountDose = it.amount_dose
        )
    }
}