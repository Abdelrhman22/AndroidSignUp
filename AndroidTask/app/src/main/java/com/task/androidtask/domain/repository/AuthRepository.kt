package com.task.androidtask.domain.repository

import com.task.androidtask.data.model.User
import com.task.androidtask.utilities.Resource

interface AuthRepository {
    suspend fun login(email: String, password: String): Resource<User>
    suspend fun submitPhoneNumber(phoneNumber: String): Resource<Unit>
    suspend fun verifyOTP(otp: String): Resource<Unit>
    suspend fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Resource<Unit>
}
