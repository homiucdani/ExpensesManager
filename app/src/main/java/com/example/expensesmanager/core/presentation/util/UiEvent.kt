package com.example.expensesmanager.core.presentation.util

sealed class UiEvent {

    data class NavigateToMainScreen(val userId: Int) : UiEvent()
    object NavigateToLogin : UiEvent()
}