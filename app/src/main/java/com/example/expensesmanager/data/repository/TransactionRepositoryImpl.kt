package com.example.expensesmanager.data.repository

import com.example.expensesmanager.data.local.dao.TransactionDao
import com.example.expensesmanager.data.mapper.toTransaction
import com.example.expensesmanager.data.mapper.toTransactionEntity
import com.example.expensesmanager.domain.model.Transaction
import com.example.expensesmanager.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl(
    private val transactionDao: TransactionDao
) : TransactionRepository {
    override suspend fun insertTransaction(transaction: Transaction) {
        return transactionDao.insertTransaction(transaction.toTransactionEntity())
    }

    override fun getAllTransaction(userId: Int): Flow<List<Transaction>> {
        return transactionDao.getAllTransaction(userId)
            .map { transactions ->
                transactions.map { transaction ->
                    transaction.toTransaction()
                }
            }
    }
}