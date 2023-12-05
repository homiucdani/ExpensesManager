package com.example.expensesmanager.presentation.login

sealed class LoginEvent {

    data class OnEmailChange(val email: String) : LoginEvent()
    data class OnPasswordChange(val password: String) : LoginEvent()
    data class OnLoginClick(val email: String) : LoginEvent()

}