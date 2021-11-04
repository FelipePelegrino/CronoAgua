package com.gmail.devpelegrino.cronoagua.util

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
        setNotify()
        return Result.success()
    }

    private fun setNotify() {
        val notificationManager = NotificationManagerCompat.from(context)
        createNotificationChannel()
        notificationManager.notify(1, getNotificationBuilder().build())
    }

    private fun getNotificationBuilder(): NotificationCompat.Builder {
        val builder =
            NotificationCompat.Builder(
                context,
                context.getString(R.string.notification_channel_id)
            )
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val intent = Intent(
            context,
            MainActivity::class.java
        )
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            0
        )

        builder.setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_text))
            .setSmallIcon(R.drawable.ic_water_drop)
            .setColor(ContextCompat.getColor(context, R.color.primary_details))
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    context.getString(R.string.notification_text)
                )
            )
            .setAutoCancel(true)
            .setSound(alarmSound)
            .setContentIntent(pendingIntent)
            .setGroup(context.getString(R.string.notification_group_key))
            .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_ALL)

        return builder
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(
                context.getString(R.string.notification_channel_id),
                context.getString(R.string.notification_channel_title),
                importance
            )
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            val notificationManager =
                context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}