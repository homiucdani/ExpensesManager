package com.example.expensesmanager.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensesmanager.core.presentation.util.UiEvent
import com.example.expensesmanager.domain.model.User
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
class RegisterViewModel @Inject constructor(
    private val formValidation: FormValidation,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    fun onEvent(event: RegisterEvent) {
        when (event) {

            is RegisterEvent.OnNameChange -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }

            is RegisterEvent.OnEmailChange -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }

            is RegisterEvent.OnPasswordChange -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
            }

            RegisterEvent.OnRegisterClick -> {
                checkValidation(
                    name = state.value.name,
                    email = state.value.email,
                    password = state.value.password
                )
            }

            else -> Unit
        }
    }

    private fun checkValidation(name: String, email: String, password: String) {
        _state.update {
            it.copy(
                isLoading = true
            )
        }

        val result = formValidation.validateRegisterForm(name, email, password)

        if (result.successful) {
            viewModelScope.launch(Dispatchers.IO) {

                val user = userRepository.getUserByEmail(email)

                if (user != null) {
                    withContext(Dispatchers.Main) {
                        _state.update {
                            it.copy(
                                nameError = null,
                                emailError = null,
                                passwordError = "User already exists.",
                                isLoading = false
                            )
                        }
                    }
                } else {
                    userRepository.insertUser(
                        User(
                            userId = 0,
                            name = name,
                            email = email,
                            password = password
                        )
                    )
                    withContext(Dispatchers.Main) {
                        // navigate to login
                        _uiEvent.emit(UiEvent.NavigateToLogin)

                        _state.update {
                            it.copy(
                                nameError = null,
                                passwordError = null,
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
                    nameError = result.nameError,
                    emailError = result.emailError,
                    passwordError = result.passwordError,
                    isLoading = false
                )
            }
        }
    }
}