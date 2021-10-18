package com.gmail.devpelegrino.cronoagua.domain

import java.time.LocalDateTime

data class Configuration(
    var notify : Boolean,
    var notifyWithVibrate: Boolean,
    var wakeUpTime: LocalDateTime,
    var timeToSleep: LocalDateTime
)
