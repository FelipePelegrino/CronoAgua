package com.gmail.devpelegrino.cronoagua.util

import android.content.Context
import androidx.work.*
import com.gmail.devpelegrino.cronoagua.ui.Constants
import java.util.concurrent.TimeUnit

class CronoAguaWork(appContext: Context) {

    var context = appContext

    private val constraints = Constraints.Builder()
        .setRequiresStorageNotLow(true)
        .build()

    private val uploadWorker = OneTimeWorkRequestBuilder<NotifyWorker>()
        .setConstraints(constraints)
        .addTag(Constants.WORKER_TAG_NOTIFY)
        .setInitialDelay(Constants.TIME_INTERVAL, TimeUnit.MINUTES)
        .build()

    fun scheduleWork() {
        WorkManager.getInstance(context).enqueue(uploadWorker)
    }

    fun cancelWork() {
        val workManager = WorkManager.getInstance(context)
        workManager.cancelAllWorkByTag(Constants.WORKER_TAG_NOTIFY)
    }
}