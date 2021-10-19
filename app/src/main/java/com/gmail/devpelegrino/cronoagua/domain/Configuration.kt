package com.gmail.devpelegrino.cronoagua.domain

data class Configuration(
    var id: Int = -1,
    var notify : Boolean = true,
    var notifyWithVibrate: Boolean = true,
    var wakeUpTime: String = "07:00",
    var timeToSleep: String = "22:00"
)

fun com.gmail.devpelegrino.cronoagua.domain.Configuration.toDatabase() = com.gmail.devpelegrino.cronoagua.database.Configuration(
    user_id = id,
    notify = notify,
    notify_with_vibrate = notifyWithVibrate,
    wake_up_time = wakeUpTime,
    time_to_sleep = timeToSleep
)