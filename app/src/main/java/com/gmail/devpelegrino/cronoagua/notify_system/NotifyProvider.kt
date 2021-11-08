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
import com.gmail.devpelegrino.cronoagua.R
import com.gmail.devpelegrino.cronoagua.ui.MainActivity
import com.gmail.devpelegrino.cronoagua.util.Constants

class NotifyProvider(appContext: Context) {

    var context: Context = appContext

    fun setNotify(builder: NotificationCompat.Builder) {
        val notificationManager = NotificationManagerCompat.from(context)
        createNotificationChannel()
        notificationManager.notify(Constants.NOTIFICATION_NOTIFY_ID, builder.build())
    }

    fun getNotificationBuilder(): NotificationCompat.Builder {
        val builder =
            NotificationCompat.Builder(
                context,
                Constants.NOTIFICATION_CHANNEL_ID
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
            .setGroup(Constants.NOTIFICATION_GROUP_KEY)
            .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_ALL)

        return builder
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_ID,
                Constants.NOTIFICATION_CHANNEL_TITLE,
                importance
            )
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            val notificationManager =
                context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}