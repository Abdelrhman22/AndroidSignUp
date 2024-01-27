package com.task.androidtask.data.repository

import com.task.androidtask.data.model.User
import com.task.androidtask.data.source.AuthDataSource
import com.task.androidtask.domain.repository.AuthRepository
import com.task.androidtask.utilities.Resource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {

    override suspend fun login(email: String, password: String): Resource<User> {
        return authDataSource.login(email, password)
    }

    override suspend fun submitPhoneNumber(phoneNumber: String): Resource<Unit> {
        return authDataSource.submitPhoneNumber(phoneNumber)
    }

    override suspend fun verifyOTP(otp: String): Resource<Unit> {
        return authDataSource.verifyOTP(otp)
    }

    override suspend fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Resource<Unit> {
        return authDataSource.signUp(firstName, lastName, email, password)
    }
}

