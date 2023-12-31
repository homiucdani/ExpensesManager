package com.example.expensesmanager.domain.model

data class User(
    val userId: Int,
    val name: String,
    val email: String,
    val password: String
)
