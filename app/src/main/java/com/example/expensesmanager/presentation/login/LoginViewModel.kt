package com.example.expensesmanager.presentation.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChange -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }

            is LoginEvent.OnPasswordChange -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
            }

            is LoginEvent.OnLoginClick -> {

            }
        }
    }
}