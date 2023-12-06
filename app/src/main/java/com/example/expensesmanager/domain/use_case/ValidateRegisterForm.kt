package com.example.expensesmanager.domain.use_case

import android.util.Patterns

class ValidateRegisterForm {

    operator fun invoke(
        name: String,
        email: String,
        password: String
    ): RegisterFormValidation {

        if (name.isBlank()) {
            return RegisterFormValidation(
                successful = false,
                nameError = "The name can't be blank."
            )
        }

        if (name.length > 22) {
            return RegisterFormValidation(
                successful = false,
                nameError = "The name can't be grater than 22 characters."
            )
        }

        if (email.isBlank()) {
            return RegisterFormValidation(
                successful = false,
                emailError = "The email can't be blank."
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return RegisterFormValidation(
                successful = false,
                emailError = "That's not a valid email."
            )
        }
        if (password.length < 6) {
            return RegisterFormValidation(
                successful = false,
                passwordError = "The password needs to consist of at least 6 characters."
            )
        }
        val containsLettersAndDigits =
            password.any { it.isDigit() } && password.any { it.isLetter() }

        if (!containsLettersAndDigits) {
            return RegisterFormValidation(
                successful = false,
                passwordError = "The password needs to contain at least one letter and digit"
            )
        }

        return RegisterFormValidation(
            true
        )
    }


    data class RegisterFormValidation(
        val successful: Boolean,
        val nameError: String? = null,
        val emailError: String? = null,
        val passwordError: String? = null,
    )
}