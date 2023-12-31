package com.example.expensesmanager.domain.repository

import com.example.expensesmanager.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun insertTransaction(transaction: Transaction)

    fun getAllTransaction(userId: Int): Flow<List<Transaction>>
}