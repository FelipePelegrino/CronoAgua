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
import android.os.CountDownTimer
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import com.gmail.devpelegrino.cronoagua.domain.DailyDrink
import com.gmail.devpelegrino.cronoagua.util.convertSecondsToHMmSs
import com.gmail.devpelegrino.cronoagua.util.getDifferenceHourMillis
import java.util.concurrent.TimeUnit

class WaterManagementFragment : Fragment() {

    private lateinit var timer: CountDownTimer
    private var isFirstTime = false
    private var isDone = false
    private var isTimeExhaust = false
    private var isTimerSet = false

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

        setConfigs()

        return binding.root
    }

    override fun onResume() {
        var toolbar = requireActivity().findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.menu.setGroupVisible(R.id.group_menu, true)

        super.onResume()
    }

    @InternalCoroutinesApi
    private fun drink() {
        if(isTimerSet) {
            timer?.cancel()
        }
        viewModel.drink()
    }

    @InternalCoroutinesApi
    private fun setConfigs() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setObservers()

        binding.buttonDrink.setOnClickListener {
            drink()
        }
    }

    @InternalCoroutinesApi
    private fun setObservers() {
        viewModel.isFirstTime.observe(
            viewLifecycleOwner,
            Observer {
                isFirstTime = it
            }
        )

        viewModel.isTimeExhaust.observe(
            viewLifecycleOwner,
            Observer {
                isTimeExhaust = it
            }
        )

        viewModel.isDoneDaily.observe(
            viewLifecycleOwner,
            Observer {
                isDone = it
            }
        )

        viewModel.setTimer.observe(
            viewLifecycleOwner,
            Observer {
                if(it) {
                    if(viewModel != null) {
                        checkTimeToCountDownTimer(viewModel.dailyDrink.value!!)
                    }
                }
            }
        )

    }

    @InternalCoroutinesApi
    private fun checkTimeToCountDownTimer(data: DailyDrink) {
        if (viewModel != null) {
            if (isDone) {
                binding.textTime.text = getString(R.string.is_done_daily)
            } else if (isFirstTime) {
                binding.textTime.text = getString(R.string.is_first_time)
                binding.textTime.textAlignment = View.TEXT_ALIGNMENT_CENTER
            } else if (isTimeExhaust) {
                binding.textTime.text = getString(R.string.is_time_exhaust)
                binding.textTime.textAlignment = View.TEXT_ALIGNMENT_CENTER
            } else {
                setCountTimer(data)
            }
        }
    }


    @InternalCoroutinesApi
    private fun setCountTimer(data: DailyDrink) {
        timer = object : CountDownTimer(
            getDifferenceHourMillis(data.lastDrinkTime, Constants.TIME_INTERVAL),
            1000
        ) {
            override fun onTick(millisUntilFinished: Long) {
                binding.textTime.text = String.format(
                    getString(R.string.next_drink),
                    convertSecondsToHMmSs(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished))
                )
            }

            override fun onFinish() {
                binding.textTime.text = getString(R.string.time_to_drink)
            }
        }
        timer.start()
        isTimerSet = true
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