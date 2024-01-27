package com.task.androidtask.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.fakestoreapp.utilities.showSnackBar
import com.task.androidtask.databinding.FragmentSignUpBinding
import com.task.androidtask.presentation.viewmodels.AuthViewModel
import com.task.androidtask.utilities.ProgressDialogUtil
import com.task.androidtask.utilities.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var navController: NavController
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSignUpBinding.inflate(layoutInflater)
        navController = findNavController()

        initListeners()
        loadingObserver()
        return binding.root
    }

    private fun loadingObserver() {
        authViewModel.signUpResult.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Status.SUCCESS -> {

                    ProgressDialogUtil.dismissProgressDialog()
                    val action = SignupFragmentDirections.actionRegisterFragmentToHomeFragment()
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
        binding.registerButton.setOnClickListener {
            authViewModel.signUp(
                binding.firstEditText.text.toString(),
                binding.lasttEditText.text.toString(),
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }
    }
}