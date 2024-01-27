package com.task.androidtask.presentation.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.fakestoreapp.utilities.showSnackBar
import com.task.androidtask.R
import com.task.androidtask.databinding.FragmentMobileNumberBinding
import com.task.androidtask.presentation.viewmodels.AuthViewModel
import com.task.androidtask.utilities.ProgressDialogUtil
import com.task.androidtask.utilities.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MobileNumberFragment : Fragment() {

    private lateinit var binding: FragmentMobileNumberBinding
    private lateinit var navController: NavController
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMobileNumberBinding.inflate(layoutInflater)
        navController = findNavController()

        initListeners()
        initObservers()
        return binding.root
    }

    private fun initObservers() {
        authViewModel.phoneNumberSubmissionResult.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {

                    ProgressDialogUtil.dismissProgressDialog()
                    val action = MobileNumberFragmentDirections.actionMobileFragmentToOtpFragment()
                    navController.navigate(action)
                }

                Status.ERROR -> {
                    ProgressDialogUtil.dismissProgressDialog()
                    showSnackBar(resource.error?.message ?: "Un Expected Error")
                }

                Status.LOADING -> {
                    ProgressDialogUtil.showProgressDialog(requireContext())
                }
            }
        }
    }

    private fun initListeners() {
        binding.otpButton.setOnClickListener {
            val phoneNumber = binding.phoneEditText.text.toString().trim()
            if (phoneNumber.isNullOrEmpty()) {
                binding.phoneTextInputLayout.error = context?.getString(R.string.str_empty_error)
                return@setOnClickListener
            }

            if (isValidPhoneNumber(phoneNumber)) {
                authViewModel.submitPhoneNumber(phoneNumber)
            } else {
                binding.phoneTextInputLayout.error = "Invalid phone number format"
            }
        }

        binding.phoneEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.phoneTextInputLayout.error = null
            }
        })
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        // Regex pattern for Egyptian mobile numbers
        val regexPattern = Regex("^(010|011|012|015)\\d{8}$")
        return regexPattern.matches(phoneNumber)
    }
}