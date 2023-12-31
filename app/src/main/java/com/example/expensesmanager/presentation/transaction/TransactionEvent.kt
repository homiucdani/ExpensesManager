package com.example.expensesmanager.presentation.transaction

import android.net.Uri
import com.example.expensesmanager.domain.model.TransactionCategory
import java.time.ZonedDateTime

sealed class TransactionEvent {

    object OnBackClick : TransactionEvent()
    object OnSaveClick : TransactionEvent()

    data class OnDateTimeChange(val zonedDateTime: ZonedDateTime) : TransactionEvent()

    data class OnAmountChange(val amount: String) : TransactionEvent()
    data class OnCategoryChange(val category: TransactionCategory) : TransactionEvent()
    data class OnDetailsChange(val details: String) : TransactionEvent()
    data class OnPictureTaken(val imageUri: Uri) : TransactionEvent()
    object ClearImage : TransactionEvent()
}