package com.gmail.devpelegrino.cronoagua.ui

import android.app.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gmail.devpelegrino.cronoagua.R
import com.gmail.devpelegrino.cronoagua.databinding.FragmentWaterManagerBinding
import com.gmail.devpelegrino.cronoagua.viewmodel.WaterManagementViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import android.media.RingtoneManager

import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat

import android.app.PendingIntent
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.gmail.devpelegrino.cronoagua.domain.DailyDrink
import java.text.SimpleDateFormat
import java.util.*


class WaterManagementFragment : Fragment() {

    @InternalCoroutinesApi
    private val viewModel: WaterManagementViewModel by lazy {
        val activity = requireNotNull(this.activity) {}
        ViewModelProvider(this, WaterManagementViewModel.Factory(activity.application)).get(
            WaterManagementViewModel::class.java
        )
    }

    private lateinit var binding: FragmentWaterManagerBinding

    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWaterManagerBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        binding.buttonTeste.setOnClickListener {
            teste()
        }

        return binding.root
    }

    override fun onResume() {
        var toolbar = requireActivity().findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.menu.setGroupVisible(R.id.group_menu, true)

        super.onResume()
    }

    @InternalCoroutinesApi
    private fun teste() {
//        Log.i("TESTE", binding.viewModel?.configuration?.value?.toString().toString())
        setNotify()
        var dailyDrink = DailyDrink()
        var calendar = Calendar.getInstance()
        val formatter = SimpleDateFormat("dd-MM-yyyy")
        val form = SimpleDateFormat("HH:mm:ss")
        Log.i("TESTE", dailyDrink.toString())
        Log.i("Teste", formatter.format(calendar.time))
        Log.i("Teste", form.format(calendar.time))
    }

    private fun setNotify() {
        val notificationManager = NotificationManagerCompat.from(requireContext())
        createNotificationChannel()
        notificationManager.notify(1, getNotificationBuilder().build())
    }

    private fun getNotificationBuilder(): NotificationCompat.Builder {
        val builder =
            NotificationCompat.Builder(
                requireContext(),
                getString(R.string.notification_channel_id)
            )
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val intent = Intent(
            requireContext(),
            MainActivity::class.java
        )
        val pendingIntent = PendingIntent.getActivity(
            requireContext(),
            0,
            intent,
            0
        )

        builder.setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_text))
            .setSmallIcon(R.drawable.ic_water_drop)
            .setColor(ContextCompat.getColor(requireContext(), R.color.primary_details))
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    getString(R.string.notification_text)
                )
            )
            .setAutoCancel(true)
            .setSound(alarmSound)
            .setContentIntent(pendingIntent)
            .setGroup(getString(R.string.notification_group_key))
            .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_ALL)

        return builder
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(
                getString(R.string.notification_channel_id),
                getString(R.string.notification_channel_title),
                importance
            )
            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            val notificationManager =
                requireContext().getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

}