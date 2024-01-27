package com.task.androidtask.domain.usecase

import com.task.androidtask.data.model.User
import com.task.androidtask.domain.repository.AuthRepository
import com.task.androidtask.utilities.Resource
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Resource<User> {
        return authRepository.login(email, password)
    }
}
