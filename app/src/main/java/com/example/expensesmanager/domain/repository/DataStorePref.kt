package com.example.expensesmanager.domain.repository

import com.example.expensesmanager.domain.model.UserOnBoard
import kotlinx.coroutines.flow.Flow

interface DataStorePref {

    suspend fun saveUserOnBoard(userOnBoard: UserOnBoard)
    fun getUserOnBoard(): Flow<UserOnBoard>
}