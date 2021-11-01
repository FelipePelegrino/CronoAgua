package com.gmail.devpelegrino.cronoagua.util

import com.gmail.devpelegrino.cronoagua.domain.Climate
import com.gmail.devpelegrino.cronoagua.domain.Configuration
import com.gmail.devpelegrino.cronoagua.domain.UserProfile
import java.math.RoundingMode
import java.time.LocalTime

fun calculateDailyAverage(userProfile: UserProfile) : Int {
    var mlBase = 25
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
        30
    } else if(age <= 14 && mlBase > 25) {
        25
    } else {
        mlBase
    }
}

//Cálcula a diferença da hora que acorda para a hora que dorme
//Como a cada 30minutos, será notificado, então em 1hora serão 2 notificações
//O objetivo é retornar a quantidade de vezes em que será notificado com base nas horas configuradas pelo usuário
//Essa quantidade de vezes de notificação, é utilizada para calcular quantos ml será "cobrada" do usuário
private fun getDifferenceBetweenHourToCalculateDrink(hourStart : LocalTime, hourEnd: LocalTime) : Int {
    var betweenHour = hourEnd.hour - hourStart.hour
    betweenHour *= 2

    if(hourEnd.minute != hourStart.minute) {
        betweenHour += 1
    }

    return betweenHour
}