package com.gmail.devpelegrino.cronoagua.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gmail.devpelegrino.cronoagua.R
import com.gmail.devpelegrino.cronoagua.databinding.FragmentWaterManagerBinding
import com.gmail.devpelegrino.cronoagua.viewmodel.WaterManagementViewModel
import kotlinx.coroutines.InternalCoroutinesApi

import android.os.CountDownTimer
import androidx.lifecycle.Observer
import com.gmail.devpelegrino.cronoagua.domain.DailyDrink
import com.gmail.devpelegrino.cronoagua.util.CronoAguaWork
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
        val worker = CronoAguaWork(requireContext())

        if(isTimerSet) {
            timer?.cancel()
            worker.cancelWork()
        }
        viewModel.drink()
        worker.scheduleWork()
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
}