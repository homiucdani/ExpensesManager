package com.example.expensesmanager.data.mapper

import com.example.expensesmanager.data.local.entities.TransactionEntity
import com.example.expensesmanager.domain.model.Transaction

fun TransactionEntity.toTransaction(): Transaction {
    return Transaction(
        userId,
        transactionId,
        transactionDate,
        transactionAmount,
        transactionCategory,
        transactionDetails,
        transactionImage
    )
}

fun Transaction.toTransactionEntity(): TransactionEntity {
    return TransactionEntity(
        userId,
        transactionId,
        transactionDate,
        transactionAmount,
        transactionCategory,
        transactionDetails,
        transactionImage
    )
}