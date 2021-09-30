package com.gmail.devpelegrino.cronoagua.domain

enum class Climate {
    COLD, HOT, VERY_HOT
}

data class UserProfile(
    var id: Int = -1,
    var name: String,
    var weight: Float,
    var age: Int,
    var isPracticeExercise: Boolean,
    var localClimate: Climate,
    var dailyAverage: Int,
    var amountDose: Int
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

