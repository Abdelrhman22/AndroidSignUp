package com.task.androidtask.data.source

import com.task.androidtask.utilities.Resource
import com.task.androidtask.data.model.User

interface AuthDataSource {
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
