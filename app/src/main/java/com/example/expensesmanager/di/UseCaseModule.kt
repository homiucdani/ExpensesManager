package com.example.expensesmanager.di

import com.example.expensesmanager.domain.use_case.FormValidation
import com.example.expensesmanager.domain.use_case.ValidateLoginForm
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun providesFormValidation(): FormValidation {
        return FormValidation(
            validateLoginForm = ValidateLoginForm()
        )
    }
}