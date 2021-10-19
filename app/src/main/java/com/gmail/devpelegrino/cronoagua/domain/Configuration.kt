package com.gmail.devpelegrino.cronoagua.domain

import java.time.LocalDateTime

data class Configuration(
    var notify : Boolean = true,
    var notifyWithVibrate: Boolean = true,
    var wakeUpTime: String = "07:00",
    var timeToSleep: String = "22:00"
)
