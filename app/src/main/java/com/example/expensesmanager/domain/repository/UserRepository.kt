package com.example.expensesmanager.domain.repository

import com.example.expensesmanager.domain.model.User

interface UserRepository {
    suspend fun insertUser(userEntity: User)

    suspend fun login(email: String, password: String): User?

    suspend fun getUserById(id: Int): User

    suspend fun getUserByEmail(email: String): User?

    suspend fun deleteUserById(id: Int)
}