package com.task.androidtask.di

import com.task.androidtask.data.repository.AuthRepositoryImpl
import com.task.androidtask.data.source.AuthDataSource
import com.task.androidtask.data.source.MockAuthDataSource
import com.task.androidtask.domain.repository.AuthRepository
import com.task.androidtask.domain.usecase.LoginUseCase
import com.task.androidtask.domain.usecase.SignUpUseCase
import com.task.androidtask.domain.usecase.SubmitPhoneNumberUseCase
import com.task.androidtask.domain.usecase.VerifyOTPUseCase
import com.task.androidtask.presentation.viewmodels.AuthViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)

object AuthModule {

    @Provides
    fun provideAuthDataSource(): AuthDataSource {
        return MockAuthDataSource()
    }

    @Provides
    fun provideAuthRepository(authDataSource: AuthDataSource): AuthRepository {
        return AuthRepositoryImpl(authDataSource)
    }


    @Provides
    fun provideLoginUseCase(authRepository: AuthRepository): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    fun provideSubmitPhoneNumberUseCase(authRepository: AuthRepository): SubmitPhoneNumberUseCase {
        return SubmitPhoneNumberUseCase(authRepository)
    }

    @Provides
    fun provideVerifyOTPUseCase(authRepository: AuthRepository): VerifyOTPUseCase {
        return VerifyOTPUseCase(authRepository)
    }

    @Provides
    fun provideSignUpUseCase(authRepository: AuthRepository): SignUpUseCase {
        return SignUpUseCase(authRepository)
    }

}
