package com.task.androidtask.domain.usecase

import com.task.androidtask.domain.repository.AuthRepository
import com.task.androidtask.utilities.Resource
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Resource<Unit> {
        return authRepository.signUp(firstName, lastName, email, password)
    }
}
