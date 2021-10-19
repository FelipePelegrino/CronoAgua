package com.gmail.devpelegrino.cronoagua.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Configuration(
    @PrimaryKey
    var user_id: Int,
    var notify : Boolean,
    var notify_with_vibrate: Boolean,
    var wake_up_time: String,
    var time_to_sleep: String
)

fun List<Configuration>.asDomainModel(): List<com.gmail.devpelegrino.cronoagua.domain.Configuration> {
    return map {
        com.gmail.devpelegrino.cronoagua.domain.Configuration(
            id = it.user_id,
            notify = it.notify,
            notifyWithVibrate = it.notify_with_vibrate,
            wakeUpTime =  it.wake_up_time,
            timeToSleep = it.time_to_sleep
        )
    }
}

fun Configuration.toModel() = com.gmail.devpelegrino.cronoagua.domain.Configuration(
    id = user_id,
    notify = notify,
    notifyWithVibrate = notify_with_vibrate,
    wakeUpTime =  wake_up_time,
    timeToSleep = time_to_sleep
)
