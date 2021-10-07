package com.gmail.devpelegrino.cronoagua.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.gmail.devpelegrino.cronoagua.R
import com.gmail.devpelegrino.cronoagua.databinding.FragmentUserProfileBinding
import com.gmail.devpelegrino.cronoagua.domain.Climate
import com.gmail.devpelegrino.cronoagua.domain.UserProfile
import com.gmail.devpelegrino.cronoagua.viewmodel.UserProfileViewModel

class UserProfileFragment : Fragment() {

    private val viewModel: UserProfileViewModel by lazy {
        val activity = requireNotNull(this.activity) {}
        ViewModelProvider(this, UserProfileViewModel.Factory(activity.application)).get(
            UserProfileViewModel::class.java
        )
    }

    private lateinit var binding: FragmentUserProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUserProfileBinding.inflate(inflater)
        viewModel.loadUserProfile()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setObserverUserProfile()
        setObserverNavigateWaterManager()
        setClickListeners()

        return binding.root
    }

    private fun actionButton() {
        if (validateFields()) {
            val climate = when (binding.radioGroup.checkedRadioButtonId) {
                binding.radioCold.id -> {
                    Climate.COLD
                }
                binding.radioHot.id -> {
                    Climate.HOT
                }
                else -> {
                    Climate.VERY_HOT
                }
            }

            viewModel.saveUserProfile(
                binding.editName.text.toString(),
                binding.editAge.text.toString().toInt(),
                binding.editWeight.text.toString().toFloat(),
                climate,
                binding.switchPhysicalActivity.isChecked
            )
            viewModel.onSaveClicked()
        } else {
            Toast.makeText(context, getText(R.string.msg_save_action_failed), Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun validateFields(): Boolean {
        return validateRadioGroup() && validateEditText(
            binding.editName,
            binding.editAge,
            binding.editWeight
        )
    }

    private fun validateRadioGroup(): Boolean {
        val radioGroup = binding.radioGroup
        return radioGroup.checkedRadioButtonId != -1
    }

    private fun validateEditText(vararg edits: EditText): Boolean {
        var isSuccess = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            edits.iterator().forEachRemaining {
                if (it.text.isEmpty()) {
                    isSuccess = false;
                }
            }
        }
        return isSuccess;
    }

    private fun setObserverNavigateWaterManager() {
        viewModel.navigateToWaterManager.observe(viewLifecycleOwner,
            Observer {
                if (it) {
                    val navController = binding.root.findNavController()
                    navController.navigate(R.id.action_userProfileFragment_to_waterManagerFragment)
                    viewModel.onNavigatedToWaterManager()
                }
            })
    }

    private fun setObserverUserProfile() {
        viewModel.userProfile.observe(viewLifecycleOwner,
            Observer {
                if (it != null ) {
                    configUserProfileObserve(it)
                }
            })
    }

    private fun configUserProfileObserve(user: UserProfile) {
        when (user.localClimate) {
            Climate.COLD -> {
                binding.radioCold.isChecked = true
            }
            Climate.HOT -> {
                binding.radioHot.isChecked = true
            }
            else -> {
                binding.radioVeryHot.isChecked = true
            }
        }
    }

    private fun setClickListeners() {
        binding.buttonSave.setOnClickListener {
            actionButton()
        }

        binding.editAge.setOnFocusChangeListener { v, hasFocus ->
            if(viewModel.userProfile != null) {
                if(Integer.valueOf(binding.editAge.text.toString()) < 1) {
                    binding.editAge.text.clear()
                }
            }
        }

        binding.editWeight.setOnFocusChangeListener { v, hasFocus ->
            if(viewModel.userProfile != null) {
                if(binding.editWeight.text.toString().toFloat() < 1f) {
                    binding.editWeight.text.clear()
                }
            }
        }
    }
}