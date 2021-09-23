package com.gmail.devpelegrino.cronoagua.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.gmail.devpelegrino.cronoagua.R
import com.gmail.devpelegrino.cronoagua.databinding.FragmentUserProfileBinding
import com.gmail.devpelegrino.cronoagua.viewmodel.UserProfileViewModel

class UserProfileFragment : Fragment() {

    private val viewModel: UserProfileViewModel by lazy {
        ViewModelProvider(this).get(UserProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentUserProfileBinding.inflate(inflater)
        binding.viewModel = ViewModelProvider(this).get(UserProfileViewModel::class.java)

        viewModel.navigateToWaterManager.observe(viewLifecycleOwner,
            Observer {
                if(it) {
                    val navController = binding.root.findNavController()
                    navController.navigate(R.id.action_userProfileFragment_to_waterManagerFragment)
                    viewModel.onNavigatedToWaterManager()
                }
            })

        binding.buttonSave.setOnClickListener { viewModel.onSaveClicked() }

        return binding.root
    }
}