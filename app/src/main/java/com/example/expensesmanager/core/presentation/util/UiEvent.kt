package com.example.expensesmanager.core.presentation.util

sealed class UiEvent {

    data class NavigateToMainScreen(val id: Int) : UiEvent()
}