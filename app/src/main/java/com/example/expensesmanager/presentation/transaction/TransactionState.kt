package com.example.expensesmanager.presentation.transaction

import com.example.expensesmanager.domain.model.TransactionCategory

data class TransactionState(
    val userId: Int = -1,
    val formattedDate: String = "",
    val dateInMillis: Long = System.currentTimeMillis(),
    val amount: String = "",
    val category: TransactionCategory = TransactionCategory.Food,
    val details: String = "",
    val imageBytes: ByteArray? = null,
    val dateError: String? = null,
    val amountError: String? = null,
    val detailsError: String? = null
)
