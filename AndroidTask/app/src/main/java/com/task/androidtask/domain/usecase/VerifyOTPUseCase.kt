package com.task.androidtask.domain.usecase

import com.task.androidtask.domain.repository.AuthRepository
import com.task.androidtask.utilities.Resource
import javax.inject.Inject

class VerifyOTPUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(otp: String): Resource<Unit> {
        return authRepository.verifyOTP(otp)
    }
}
