package com.gmail.devpelegrino.cronoagua.util

import android.content.Context
import androidx.work.*
import com.gmail.devpelegrino.cronoagua.ui.Constants
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

class CronoAguaWork(appContext: Context) {

    var context = appContext

    private val constraints = Constraints.Builder()
        .setRequiresStorageNotLow(true)
        .build()

    private fun createWorkerTimeIntervalNotify(): OneTimeWorkRequest {
        return OneTimeWorkRequestBuilder<NotifyWorker>()
            .setConstraints(constraints)
            .addTag(Constants.WORKER_TAG_NOTIFY_TIME_INTERVAL)
            .setInitialDelay(Constants.TIME_INTERVAL, TimeUnit.MINUTES)
            .build()
    }

    private fun createWorkerWakeUpNotifyPeriodic(): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<NotifyWorker>(24, TimeUnit.HOURS)
            .setConstraints(constraints)
            .addTag(Constants.WORKER_TAG_NOTIFY_WAKEUP_TIME)
            .setInitialDelay(Constants.TIME_INTERVAL, TimeUnit.MINUTES)
            .build()
    }

    fun scheduleWork(tag: String, hour: Int) {
        if(tag == Constants.WORKER_TAG_NOTIFY_TIME_INTERVAL) {
            WorkManager.getInstance(context).enqueue(createWorkerTimeIntervalNotify())
        } else{
            if(LocalDateTime.now().hour <= hour) {
                WorkManager.getInstance(context).enqueue(createWorkerWakeUpNotifyPeriodic())
            }
        }

    }

    fun cancelWork(tag: String) {
        val workManager = WorkManager.getInstance(context)
        if(tag == Constants.WORKER_TAG_NOTIFY_TIME_INTERVAL) {
            workManager.cancelAllWorkByTag(Constants.WORKER_TAG_NOTIFY_TIME_INTERVAL)
        } else {
            workManager.cancelAllWorkByTag(Constants.WORKER_TAG_NOTIFY_WAKEUP_TIME)
        }
    }
}