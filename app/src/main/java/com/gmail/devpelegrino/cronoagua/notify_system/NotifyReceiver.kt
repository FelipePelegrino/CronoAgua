package com.gmail.devpelegrino.cronoagua.notify_system

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.gmail.devpelegrino.cronoagua.ui.WaterManagementFragment
import com.gmail.devpelegrino.cronoagua.util.Constants

class NotifyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val intent = Intent(context, WaterManagementFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            Constants.INTENT_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notifyProvider = NotifyProvider(context!!)
        val builder = notifyProvider.getNotificationBuilder()
        builder.setContentIntent(pendingIntent)
        notifyProvider.setNotify(builder)

    }
}