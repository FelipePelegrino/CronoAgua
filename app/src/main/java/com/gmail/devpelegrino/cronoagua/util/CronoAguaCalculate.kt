package com.gmail.devpelegrino.cronoagua.util

import com.gmail.devpelegrino.cronoagua.domain.Climate
import com.gmail.devpelegrino.cronoagua.domain.Configuration
import com.gmail.devpelegrino.cronoagua.domain.UserProfile
import com.gmail.devpelegrino.cronoagua.ui.Constants
import java.math.RoundingMode
import java.time.LocalTime

fun calculateDailyAverage(userProfile: UserProfile) : Int {
    var mlBase = Constants.ML_BASE
    mlBase = calculateClimate(mlBase, userProfile.localClimate)
    mlBase = calculatePracticeExercise(mlBase, userProfile.isPracticeExercise)
    mlBase = calculateAge(mlBase, userProfile.age)
    var weightformated = userProfile.weight.toBigDecimal().setScale(1, RoundingMode.UP).toDouble()

    return (mlBase * weightformated.toInt())
}

fun calculateAmountDose(userProfile: UserProfile, configuration: Configuration) : Int {
    var betweenTime = getDifferenceBetweenHourToCalculateDrink(getTime(configuration.wakeUpTime), getTime(configuration.timeToSleep))

    return userProfile.dailyAverage / betweenTime
}

private fun calculateClimate(mlBase : Int, climate: Climate) : Int {
    return mlBase + when(climate) {
        Climate.HOT -> 5
        Climate.VERY_HOT -> 10
        else -> 0
    }
}

private fun calculatePracticeExercise(mlBase : Int, isPracticeExercise: Boolean) : Int {
    return if(isPracticeExercise) {
        mlBase + 5
    } else {
        mlBase
    }
}

private fun calculateAge(mlBase : Int, age: Int) : Int {
    return if(age >= 60 && mlBase > 30) {
        35
    } else if(age <= 14 && mlBase > 25) {
        30
    } else {
        mlBase
    }
}

//Cálcula a diferença da hora que acorda para a hora que dorme
//O objetivo é retornar a quantidade de vezes em que será notificado com base nas horas configuradas pelo usuário
//Essa quantidade de vezes de notificação, é utilizada para calcular quantos ml será "cobrada" do usuário
private fun getDifferenceBetweenHourToCalculateDrink(hourStart : LocalTime, hourEnd: LocalTime) : Int {
    return  hourEnd.hour - hourStart.hour
}