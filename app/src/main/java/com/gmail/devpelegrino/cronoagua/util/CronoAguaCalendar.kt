package com.gmail.devpelegrino.cronoagua.util

import android.util.Log
import java.time.*
import java.time.format.DateTimeFormatter

fun getDailyDate(): LocalDate {
    return OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).toLocalDate()
}

fun getHoursDaily(): LocalTime {
    return LocalTime.now()
}

fun getTime(value: String) : LocalTime {
    return LocalTime.parse(value)
}

//TODO: rever
fun getDifferenceHour(hour: LocalTime, plus: Long): String {
    var formatter  = DateTimeFormatter.ofPattern("HH:mm:ss")
    var nextDrinkTime = hour.plusHours(plus)
    var remain = nextDrinkTime.minusHours(OffsetTime.now().hour.toLong())
    remain = nextDrinkTime.minusMinutes(OffsetTime.now().minute.toLong())
    remain = nextDrinkTime.minusSeconds(OffsetTime.now().second.toLong())

    return remain.format(formatter)
}