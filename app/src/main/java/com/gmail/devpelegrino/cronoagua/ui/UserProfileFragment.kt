package com.gmail.devpelegrino.cronoagua.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
                if (it) {
                    val navController = binding.root.findNavController()
                    navController.navigate(R.id.action_userProfileFragment_to_waterManagerFragment)
                    viewModel.onNavigatedToWaterManager()
                }
            })

        binding.buttonSave.setOnClickListener {
            if (validateEditText(
                    binding.editName,
                    binding.editAge,
                    binding.editWeight
                )
            ) {
                Log.i("TESTE", "deu certo :D")
                viewModel.saveUserProfile(
                    binding.editName.text.toString(),
                    binding.editAge.text.toString().toInt(),
                    binding.editWeight.text.toString().toFloat()
                )
            } else {
                Log.i("TESTE", "deu ruim D:")
            }
//            viewModel.onSaveClicked()
        }

        return binding.root
    }

    fun validateEditText(vararg edits: EditText): Boolean {
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
}