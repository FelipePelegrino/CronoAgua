package com.gmail.devpelegrino.cronoagua.domain

import java.time.OffsetDateTime
import java.time.OffsetTime

data class DailyDrink constructor(
    var date: OffsetDateTime? = null,
    var totalAmountWater: Int = 0,
    var currentAmountWater: Int = 0,
    var lastDrinkTime: OffsetDateTime? = null,
    var timeInterval: OffsetTime? = null
)

fun com.gmail.devpelegrino.cronoagua.domain.DailyDrink.toDatabase() = com.gmail.devpelegrino.cronoagua.database.DailyDrink(
    date = date,
    total_amount_water = totalAmountWater,
    current_amount_water = currentAmountWater,
    last_drink_time = lastDrinkTime,
    time_interval = timeInterval
)