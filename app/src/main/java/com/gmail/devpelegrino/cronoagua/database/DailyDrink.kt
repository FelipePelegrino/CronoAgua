package com.gmail.devpelegrino.cronoagua.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity
data class DailyDrink constructor(
    @PrimaryKey
    var date: LocalDate = LocalDate.now(),
    var total_amount_water: Int = 0,
    var current_amount_water: Int = 0,
    var last_drink_time: LocalTime = LocalTime.now(),
    var time_interval: Int = 0
)

fun com.gmail.devpelegrino.cronoagua.database.DailyDrink.toModel() = com.gmail.devpelegrino.cronoagua.domain.DailyDrink(
    date = date,
    totalAmountWater = total_amount_water,
    currentAmountWater = current_amount_water,
    lastDrinkTime = last_drink_time,
    timeInterval = time_interval
)