package com.example.expensesmanager.presentation.login

import androidx.lifecycle.ViewModel
import com.example.expensesmanager.domain.use_case.FormValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val formValidation: FormValidation
) : ViewModel() {

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

            LoginEvent.OnLoginClick -> {
                checkValidation(state.value.email, state.value.password)
            }
        }
    }

    private fun checkValidation(email: String, password: String) {
        val result = formValidation.validateLoginForm(email, password)

        if (result.successful) {
            _state.update {
                it.copy(
                    isSuccessfullyLoggedIn = true,
                    emailError = null,
                    passwordError = null
                )
            }
        } else {
            _state.update {
                it.copy(
                    emailError = result.emailError,
                    passwordError = result.passwordError
                )
            }
        }
    }
}