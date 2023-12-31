package com.example.expensesmanager.domain.use_case


class ValidateTransactionForm {
    operator fun invoke(
        date: String,
        amount: String,
        details: String
    ): TransactionForm {
        if (date.isBlank()) {
            return TransactionForm(
                successful = false,
                dateError = "Date cannot be empty."
            )
        }
        if (amount.isBlank()) {
            return TransactionForm(
                successful = false,
                amountError = "Amount cannot be empty."
            )
        }

        if (details.isBlank()){
            return TransactionForm(
                successful = false,
                detailsError = "Details cannot be empty."
            )
        }

        return TransactionForm(
            successful = true
        )
    }

    data class TransactionForm(
        val successful: Boolean,
        val dateError: String? = null,
        val amountError: String? = null,
        val detailsError: String? = null
    )
}