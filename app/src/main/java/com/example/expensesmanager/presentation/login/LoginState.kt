package com.example.expensesmanager.presentation.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null
)
