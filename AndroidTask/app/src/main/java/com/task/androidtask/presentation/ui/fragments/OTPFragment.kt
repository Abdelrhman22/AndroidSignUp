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
import com.task.androidtask.databinding.FragmentOtpBinding
import com.task.androidtask.presentation.viewmodels.AuthViewModel
import com.task.androidtask.utilities.ProgressDialogUtil
import com.task.androidtask.utilities.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OTPFragment : Fragment() {

    private lateinit var binding: FragmentOtpBinding
    private lateinit var navController: NavController
    private val authViewModel: AuthViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentOtpBinding.inflate(layoutInflater)
        navController = findNavController()

        initListeners()
        initObservers()
        binding.otpEditText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.otpTextInputLayout.error = null
            }
        })
        return binding.root
    }

    private fun initListeners() {
        binding.verifyButton.setOnClickListener {
            val otp = binding.otpEditText.text.toString()
            if (otp.length != 5)
                binding.otpTextInputLayout.error = "OTP Should be 5 numbers"
            else
                authViewModel.verifyOTP(otp)
        }
    }

    private fun initObservers() {
        authViewModel.otpVerificationResult.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {

                    ProgressDialogUtil.dismissProgressDialog()
                    val action = OTPFragmentDirections.actionOtpFragmentToRegisterFragment()
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
}