package com.gmail.devpelegrino.cronoagua.notify_system

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotifyWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    private var context = appContext

    override fun doWork(): Result {
        val notifyProvider = NotifyProvider(context)
        notifyProvider.setNotify(notifyProvider.getNotificationBuilder())
        return Result.success()
    }
}