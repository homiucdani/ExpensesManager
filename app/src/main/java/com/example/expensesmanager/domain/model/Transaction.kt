package com.example.expensesmanager.domain.model

data class Transaction(
    val userId: Int,
    val transactionId: Long,
    val transactionDate: Long,
    val transactionAmount: Double,
    val transactionCategory: TransactionCategory,
    val transactionDetails: String,
    val transactionImage: ByteArray?
)
