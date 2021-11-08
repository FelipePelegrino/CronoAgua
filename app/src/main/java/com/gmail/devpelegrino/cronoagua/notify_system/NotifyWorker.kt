package com.gmail.devpelegrino.cronoagua.notify_system

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.gmail.devpelegrino.cronoagua.R
import com.gmail.devpelegrino.cronoagua.ui.MainActivity

class NotifyWorker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {

    private var context = appContext

    override fun doWork(): Result {
        val notifyProvider = NotifyProvider(context)
        notifyProvider.setNotify(notifyProvider.getNotificationBuilder())
        return Result.success()
    }
}