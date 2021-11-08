package com.gmail.devpelegrino.cronoagua.util

import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

fun getDailyDate(): LocalDate {
    return OffsetDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).toLocalDate()
}

fun getHoursDaily(): LocalTime {
    return LocalTime.now()
}

fun getTime(value: String) : LocalTime {
    return LocalTime.parse(value)
}

fun getDifferenceLocalTime(hour: LocalTime, plus: Long): LocalTime {
    var nextDrinkTime = getNexDrinkTime(hour, plus)
    var remain = nextDrinkTime.minusHours(OffsetTime.now().hour.toLong())
    remain = remain.minusMinutes(OffsetTime.now().minute.toLong())
    remain = remain.minusSeconds(OffsetTime.now().second.toLong())
    return remain
}

fun getNexDrinkTime(hour: LocalTime, plus: Long): LocalTime {
    return hour.plusMinutes(plus)
}

fun getIsTimeExhaust(hour: LocalTime, plus: Long): Boolean {
    var nextDrinkTime = getNexDrinkTime(hour, plus)
    var now = LocalTime.now()

    return now.isAfter(nextDrinkTime)
}

fun getDifferenceHourMillis(hour: LocalTime, plus: Long): Long {
    return (getDifferenceLocalTime(hour, plus).toSecondOfDay()*1000).toLong()
}

fun getDifferenceHour(hour: LocalTime, plus: Long): String {
    var formatter  = DateTimeFormatter.ofPattern("HH:mm:ss")

    return getDifferenceLocalTime(hour, plus).format(formatter)
}

fun convertSecondsToHMmSs(seconds: Long): String? {
    val s = seconds % 60
    val m = seconds / 60 % 60
    val h = seconds / (60 * 60) % 24
    return String.format("%d:%02d:%02d", h, m, s)
}

fun getCalendarConfigureToWakeUpNotify(hour: Int): Calendar {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = System.currentTimeMillis()

    if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= hour) {
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }
    calendar.set(Calendar.HOUR_OF_DAY, hour)
    calendar.set(Calendar.MINUTE, 0)

    return calendar
}