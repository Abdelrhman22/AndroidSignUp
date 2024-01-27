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
import com.task.androidtask.databinding.FragmentLoginBinding
import com.task.androidtask.presentation.viewmodels.AuthViewModel
import com.task.androidtask.utilities.ProgressDialogUtil
import com.task.androidtask.utilities.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}".toRegex()

    //    private lateinit var authViewModel: AuthViewModel
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(layoutInflater)
        navController = findNavController()
//        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        initListeners()
        loadingObserver()
        return binding.root
    }

    private fun initListeners() {
        binding.txtRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToMobileNumberFragment()
            navController.navigate(action)
        }

        binding.loginButton.setOnClickListener {
            if (isValid())
                authViewModel.login(
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                )
        }


        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.emailTextInputLayout.error = null
            }
        })

        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.passwordTextInputLayout.error = null
            }
        })

    }

    private fun isValid(): Boolean {

        if (binding.emailEditText.text.isNullOrEmpty()) {
            binding.emailTextInputLayout.error = context?.getString(R.string.str_empty_error)
        }
        if (binding.passwordEditText.text.isNullOrEmpty()) {
            binding.passwordTextInputLayout.error = context?.getString(R.string.str_empty_error)
        }

        if (binding.emailEditText.text.isNullOrEmpty() || binding.passwordEditText.text.isNullOrEmpty())
            return false

        val email = binding.emailEditText.text.toString()
        if (!emailPattern.matches(email)) {
            // Invalid email, show error message to the user
            showSnackBar("Invalid email address")
            return false
        }
        return true
    }

    private fun loadingObserver() {
        authViewModel.loginResult.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    // open home screen
                    ProgressDialogUtil.dismissProgressDialog()
                    val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
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