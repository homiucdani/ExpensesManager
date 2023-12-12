package com.example.expensesmanager.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensesmanager.domain.repository.DataStorePref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStorePref: DataStorePref
) : ViewModel() {

    private val _state = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state

    init {
        observeUserOnBoarding()
    }

    private fun observeUserOnBoarding() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStorePref.getUserOnBoard().collect { userOnBoard ->
                withContext(Dispatchers.Main) {
                    _state.update {
                        it.copy(
                            onBoardSuccessfully = userOnBoard.onBoardSuccessfully,
                            userId = userOnBoard.userId
                        )
                    }
                }
            }
        }
    }
}