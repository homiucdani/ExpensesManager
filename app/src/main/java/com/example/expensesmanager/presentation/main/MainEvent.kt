package com.example.expensesmanager.presentation.main

sealed class MainEvent {
    object Logout : MainEvent()
    object NavigateToTransaction : MainEvent()
}