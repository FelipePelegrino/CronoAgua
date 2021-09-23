package com.gmail.devpelegrino.cronoagua.domain

data class UserProfile(
    var name: String,
    var weight: Float,
    var age: Int,
    var isPracticeExercise: Boolean,
    var localClimate: Climate,
    var dailyAverage: Int,
    var amountDose: Int
)

enum class Climate {
    COLD, HOT, VERY_HOT
}