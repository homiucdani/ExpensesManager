package com.example.expensesmanager.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensesmanager.core.presentation.util.UiEvent
import com.example.expensesmanager.domain.repository.UserRepository
import com.example.expensesmanager.domain.use_case.FormValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val formValidation: FormValidation,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

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

            else -> Unit
        }
    }

    private fun checkValidation(email: String, password: String) {
        _state.update {
            it.copy(
                isLoading = true
            )
        }

        val result = formValidation.validateLoginForm(email, password)

        if (result.successful) {
            viewModelScope.launch(Dispatchers.IO) {

                val user = userRepository.login(email, password)

                withContext(Dispatchers.Main) {
                    if (user != null) {
                        // navigate to main with the id if the user exists
                        _uiEvent.emit(UiEvent.NavigateToMainScreen(user.id))

                        _state.update {
                            it.copy(
                                isSuccessfullyLoggedIn = true,
                                emailError = null,
                                passwordError = null,
                                isLoading = false
                            )
                        }
                    } else {
                        _state.update {
                            it.copy(
                                isSuccessfullyLoggedIn = false,
                                passwordError = "Wrong email or password",
                                isLoading = false,
                                emailError = null
                            )
                        }
                    }
                }
            }
        } else {
            _state.update {
                it.copy(
                    emailError = result.emailError,
                    passwordError = result.passwordError,
                    isLoading = false
                )
            }
        }
    }
}