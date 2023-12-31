package com.example.expensesmanager.presentation.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensesmanager.core.presentation.util.UiEvent
import com.example.expensesmanager.domain.repository.DataStorePref
import com.example.expensesmanager.domain.repository.UserRepository
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
class MainViewModel @Inject constructor(
    private val dataStorePref: DataStorePref,
    private val userRepository: UserRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    init {
        getUserById()
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            MainEvent.Logout -> {
                logoutUser()
            }

            else -> Unit
        }
    }

    private fun getUserById() {
        val userId = savedStateHandle.get<Int>("userId")
        if (userId != null && userId != -1) {
            viewModelScope.launch(Dispatchers.IO) {
                val user = userRepository.getUserById(userId)
                withContext(Dispatchers.Main) {
                    _state.update {
                        it.copy(
                            userId = userId,
                            username = user.name
                        )
                    }
                }
            }
        }
    }

    private fun logoutUser() {
        _state.update {
            it.copy(
                loggingOff = true
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            dataStorePref.logoutUser()
            _uiEvent.emit(UiEvent.NavigateToLogin)
        }
    }
}