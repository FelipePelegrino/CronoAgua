package com.gmail.devpelegrino.cronoagua.domain

import java.time.LocalDate
import java.time.LocalTime
import java.time.OffsetTime

data class DailyDrink constructor(
    var date: LocalDate,
    var totalAmountWater: Int = 0,
    var currentAmountWater: Int = 0,
    var lastDrinkTime: LocalTime = LocalTime.now(),
    var timeInterval: Int = 0
)

fun com.gmail.devpelegrino.cronoagua.domain.DailyDrink.toDatabase() = com.gmail.devpelegrino.cronoagua.database.DailyDrink(
    date = date,
    total_amount_water = totalAmountWater,
    current_amount_water = currentAmountWater,
    last_drink_time = lastDrinkTime,
    time_interval = timeInterval
)