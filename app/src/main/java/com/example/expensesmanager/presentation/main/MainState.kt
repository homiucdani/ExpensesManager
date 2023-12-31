package com.example.expensesmanager.presentation.main

data class MainState(
    val userId: Int = -1,
    val username: String = "Unknown",
    val loggingOff: Boolean = false
)
