package com.example.expensesmanager.domain.use_case

import android.util.Patterns

class ValidateLoginForm {

    operator fun invoke(
        email: String,
        password: String
    ): LoginFormValidation {
        if (email.isBlank()) {
            return LoginFormValidation(
                successful =false,
                emailError = "The email can't be blank."
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return LoginFormValidation(
                successful = false,
                emailError = "That's not a valid email."
            )
        }
        if (password.length <= 6) {
            return LoginFormValidation(
                successful = false,
                passwordError = "The password needs to consist of at least 6 characters."
            )
        }
        val containsLettersAndDigits =
            password.any { it.isDigit() } && password.any { it.isLetter() }

        if (!containsLettersAndDigits) {
            return LoginFormValidation(
                successful = false,
                passwordError = "The password needs to contain at least one letter and digit"
            )
        }

        return LoginFormValidation(
            true
        )
    }


    data class LoginFormValidation(
        val successful: Boolean,
        val emailError: String? = null,
        val passwordError: String? = null,
    )
}

