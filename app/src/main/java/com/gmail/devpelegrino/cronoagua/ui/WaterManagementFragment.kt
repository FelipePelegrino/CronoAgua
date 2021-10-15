package com.gmail.devpelegrino.cronoagua.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.gmail.devpelegrino.cronoagua.R
import com.gmail.devpelegrino.cronoagua.databinding.FragmentWaterManagerBinding

class WaterManagementFragment : Fragment() {

    private lateinit var binding : FragmentWaterManagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWaterManagerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        var toolbar = requireActivity().findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.menu.setGroupVisible(R.id.group_menu, true)

        super.onResume()
    }
}