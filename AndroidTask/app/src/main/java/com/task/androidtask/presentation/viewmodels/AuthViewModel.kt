package com.task.androidtask.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.androidtask.data.model.User
import com.task.androidtask.domain.usecase.LoginUseCase
import com.task.androidtask.domain.usecase.SignUpUseCase
import com.task.androidtask.domain.usecase.SubmitPhoneNumberUseCase
import com.task.androidtask.domain.usecase.VerifyOTPUseCase
import com.task.androidtask.utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val submitPhoneNumberUseCase: SubmitPhoneNumberUseCase,
    private val verifyOTPUseCase: VerifyOTPUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _loginResult = MutableLiveData<Resource<User>>()
    val loginResult: LiveData<Resource<User>> = _loginResult

    private val _phoneNumberSubmissionResult = MutableLiveData<Resource<Unit>>()
    val phoneNumberSubmissionResult: LiveData<Resource<Unit>> = _phoneNumberSubmissionResult

    private val _otpVerificationResult = MutableLiveData<Resource<Unit>>()
    val otpVerificationResult: LiveData<Resource<Unit>> = _otpVerificationResult

    private val _signUpResult = MutableLiveData<Resource<Unit>>()
    val signUpResult: LiveData<Resource<Unit>> = _signUpResult

    // Implementation

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = Resource.loading()
            val result = loginUseCase(email, password)
            _loginResult.value = result
        }
    }

    fun submitPhoneNumber(phoneNumber: String) {
        viewModelScope.launch {
            _phoneNumberSubmissionResult.value = Resource.loading()
            val result = submitPhoneNumberUseCase(phoneNumber)
            _phoneNumberSubmissionResult.value = result
        }
    }

    fun verifyOTP(otp: String) {
        viewModelScope.launch {
            _otpVerificationResult.value = Resource.loading()
            val result = verifyOTPUseCase(otp)
            _otpVerificationResult.value = result
        }
    }

    fun signUp(firstName: String, lastName: String, email: String, password: String) {
        viewModelScope.launch {
            _signUpResult.value = Resource.loading()
            val result = signUpUseCase(firstName, lastName, email, password)
            _signUpResult.value = result
        }
    }
}
