package com.gmail.devpelegrino.cronoagua.notify_system

import android.app.IntentService
import android.app.PendingIntent
import android.content.Intent
import com.gmail.devpelegrino.cronoagua.ui.WaterManagementFragment
import com.gmail.devpelegrino.cronoagua.util.Constants

class NotifyIntentService : IntentService("NotifyIntentService") {
    override fun onHandleIntent(intent: Intent?) {
        val intent = Intent(this, WaterManagementFragment::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            Constants.INTENT_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notifyProvider = NotifyProvider(applicationContext)
        val builder = notifyProvider.getNotificationBuilder()
        builder.setContentIntent(pendingIntent)
        notifyProvider.setNotify(builder)
    }
}