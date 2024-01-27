package com.task.androidtask.data.source

import com.task.androidtask.data.model.User
import com.task.androidtask.utilities.Resource
import kotlinx.coroutines.delay


class MockAuthDataSource : AuthDataSource {

    override suspend fun login(email: String, password: String): Resource<User> {
        delay(5000)
        // Simulate login failure if email and password do not match
        if (email != "abdoarafa@gmail.com" || password != "000000") {
            return Resource.error(Throwable("Incorrect email or password"))
        }
        // Simulate login success
        val user = User("abdo", "arafa", email, password)
        return Resource.success(user)
    }

    override suspend fun submitPhoneNumber(phoneNumber: String): Resource<Unit> {
        delay(5000)
        return if (phoneNumber == "01097483095")
            Resource.success(Unit)
        else
            Resource.error(Throwable("Not working mobile number"))
    }

    override suspend fun verifyOTP(otp: String): Resource<Unit> {
        delay(5000)
        // Simulate OTP verification
        return if (otp == "12345")
            Resource.success(Unit)
        else
            Resource.error(Throwable("Invalid OTP"))
    }

    override suspend fun signUp(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ): Resource<Unit> {
        delay(5000)
        // Simulate sign up process
        return Resource.success(Unit)
    }
}
