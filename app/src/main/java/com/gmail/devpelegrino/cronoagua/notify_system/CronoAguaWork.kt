package com.gmail.devpelegrino.cronoagua.notify_system

import android.content.Context
import androidx.work.*
import com.gmail.devpelegrino.cronoagua.util.Constants
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

    fun scheduleWork() {
        WorkManager.getInstance(context).enqueue(createWorkerTimeIntervalNotify())
    }

    fun cancelWork() {
        val workManager = WorkManager.getInstance(context)
        workManager.cancelAllWorkByTag(Constants.WORKER_TAG_NOTIFY_TIME_INTERVAL)
    }
}