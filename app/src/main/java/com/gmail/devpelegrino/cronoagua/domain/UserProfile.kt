package com.gmail.devpelegrino.cronoagua.domain

import com.gmail.devpelegrino.cronoagua.database.UserProfile

enum class Climate {
    COLD, HOT, VERY_HOT
}

data class UserProfile(
    var id: Int = -1,
    var name: String = "",
    var weight: Float = 0f,
    var age: Int = 0,
    var isPracticeExercise: Boolean = false,
    var localClimate: Climate = Climate.COLD,
    var dailyAverage: Int = 0,
    var amountDose: Int = 0
) {
    val shortName: String
        get() = if (name.indexOf(" ") != -1)  name.split(" ")[0] else name

    val ageDescription: String
        get() = String.format("%d anos", age)

    val dailyAverageDescription: String
        get() = String.format("%d ml", dailyAverage)

    val amountDoseDescription: String
        get() = String.format("%d ml", amountDose)
}

fun com.gmail.devpelegrino.cronoagua.domain.UserProfile.toUserProfileDatabase() = com.gmail.devpelegrino.cronoagua.database.UserProfile(
    user_id = id,
    name = name,
    weight = weight,
    age = age,
    is_practice_exercise =  isPracticeExercise,
    local_climate =  localClimate,
    daily_average = dailyAverage,
    amount_dose = amountDose
)

