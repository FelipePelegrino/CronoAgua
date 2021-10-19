package com.gmail.devpelegrino.cronoagua.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.gmail.devpelegrino.cronoagua.R
import com.gmail.devpelegrino.cronoagua.databinding.FragmentConfigurationBinding
import com.gmail.devpelegrino.cronoagua.viewmodel.ConfigurationViewModel


class ConfigurationFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private val viewModel: ConfigurationViewModel by lazy {
        val activity = requireNotNull(this.activity) {}
        ViewModelProvider(this, ConfigurationViewModel.Factory(activity.application)).get(
            ConfigurationViewModel::class.java
        )
    }

    private lateinit var binding: FragmentConfigurationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentConfigurationBinding.inflate(inflater)
        binding.viewModel = viewModel

        setSpinners()
        setListeners()
        setObservers()

        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            binding.spinnerWakeUp.id -> {
                binding.viewModel?.changeWakeUpTime(
                    requireContext(),
                    parent?.getItemAtPosition(position).toString()
                )
            }
            binding.spinnerToSleep.id -> {
                binding.viewModel?.changeToSleepTime(
                    requireContext(),
                    parent?.getItemAtPosition(position).toString()
                )
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun setListeners() {
        binding.switchNotify.setOnCheckedChangeListener { button, checked ->
            binding.viewModel?.changeNotify(requireContext(), checked)
        }

        binding.switchNotifyVibrate.setOnCheckedChangeListener { button, checked ->
            binding.viewModel?.changeVibrate(requireContext(), checked)
        }

        binding.spinnerWakeUp.onItemSelectedListener = this

        binding.spinnerToSleep.onItemSelectedListener = this
    }

    private fun setSpinners() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.wakeUpTimes,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerWakeUp.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.toSleepTimes,
            android.R.layout.simple_spinner_dropdown_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerToSleep.adapter = adapter
        }
    }

    private fun setObservers() {
        binding.viewModel?.configuration?.observe(viewLifecycleOwner, {
            if (it != null) {
                val arrayWakeUp = resources.getStringArray(R.array.wakeUpTimes)
                val arrayToSleep = resources.getStringArray(R.array.toSleepTimes)
                if (arrayWakeUp.contains(it.wakeUpTime)) {
                    binding.spinnerWakeUp.setSelection(arrayWakeUp.indexOf(it.wakeUpTime))
                }
                if (arrayToSleep.contains(it.timeToSleep)) {
                    binding.spinnerToSleep.setSelection(arrayToSleep.indexOf(it.timeToSleep))
                }
            }
        })
    }
}