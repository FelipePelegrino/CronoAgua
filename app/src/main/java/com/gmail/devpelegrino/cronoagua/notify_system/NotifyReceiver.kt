package com.gmail.devpelegrino.cronoagua.notify_system

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class NotifyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val intent = Intent(context, NotifyIntentService::class.java)
        Log.i("teste $this", "rodei rec")
        context?.startService(intent)

    }
}