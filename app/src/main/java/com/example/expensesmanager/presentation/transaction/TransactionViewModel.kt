package com.example.expensesmanager.presentation.transaction

import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.expensesmanager.core.domain.util.Constants
import com.example.expensesmanager.domain.model.Transaction
import com.example.expensesmanager.domain.repository.TransactionRepository
import com.example.expensesmanager.domain.use_case.ValidateTransactionForm
import com.example.expensesmanager.util.formatDateToPattern
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val application: Application,
    private val validateTransactionForm: ValidateTransactionForm,
    private val transactionRepository: TransactionRepository,
    private val savedStateHandle: SavedStateHandle
) : AndroidViewModel(application) {

    private fun getContext(): Context = getApplication<Application>().applicationContext

    private val _state = MutableStateFlow(TransactionState())
    val state: StateFlow<TransactionState> = _state

    init {
        getUserId()
    }

    fun onEvent(event: TransactionEvent) {
        when (event) {
            is TransactionEvent.OnDateTimeChange -> {
                val date = formatDateToPattern(event.zonedDateTime, Constants.DATE_FORMAT)
                _state.update {
                    it.copy(
                        formattedDate = date,
                        dateInMillis = event.zonedDateTime.toInstant().toEpochMilli()
                    )
                }
            }

            is TransactionEvent.OnAmountChange -> {
                val strBuilder = StringBuilder()
                var count = 0
                if (event.amount.isNotBlank()) {
                    event.amount.forEach { char ->
                        if (char != ',') {
                            if (char == '.') {
                                if (count == 0) {
                                    strBuilder.append(char)
                                    count++
                                }
                            } else {
                                strBuilder.append(char)
                            }
                        }
                        _state.update {
                            it.copy(
                                amount = strBuilder.toString()
                            )
                        }
                    }
                } else {
                    _state.update {
                        it.copy(
                            amount = ""
                        )
                    }
                }
            }


            is TransactionEvent.OnCategoryChange -> {
                _state.update {
                    it.copy(
                        category = event.category
                    )
                }
            }

            is TransactionEvent.OnDetailsChange -> {
                _state.update {
                    it.copy(
                        details = event.details
                    )
                }
            }

            is TransactionEvent.OnPictureTaken -> {
                viewModelScope.launch(Dispatchers.IO) {
                    getTakenPhotoBytes(imageUri = event.imageUri)
                }
            }

            TransactionEvent.ClearImage -> {
                _state.update {
                    it.copy(
                        imageBytes = null
                    )
                }
            }

            TransactionEvent.OnSaveClick -> {
                saveTransaction(state.value)
            }

            else -> Unit
        }
    }

    private fun getTakenPhotoBytes(imageUri: Uri?) {
        imageUri?.let { imgUri ->
            getContext().contentResolver.openInputStream(imgUri)?.use { inputStream ->
                _state.update {
                    it.copy(
                        imageBytes = inputStream.readBytes()
                    )
                }
            }
        }
    }

    private fun saveTransaction(
        state: TransactionState
    ) {
        val isTransactionSuccessful =
            checkTransactionForm(state.formattedDate, state.amount, state.details)
        if (isTransactionSuccessful) {
            viewModelScope.launch(Dispatchers.IO) {
                transactionRepository.insertTransaction(
                    Transaction(
                        userId = state.userId,
                        transactionId = 0,
                        transactionDate = state.dateInMillis,
                        transactionAmount = state.amount.toDouble(),
                        transactionCategory = state.category,
                        transactionDetails = state.details,
                        transactionImage = state.imageBytes
                    )
                )
            }
        }
    }

    private fun checkTransactionForm(
        date: String,
        amount: String,
        details: String
    ): Boolean {
        val validate = validateTransactionForm(date, amount, details)
        return if (validate.successful) {
            true
        } else {
            _state.update {
                it.copy(
                    dateError = validate.dateError,
                    amountError = validate.amountError,
                    detailsError = validate.detailsError
                )
            }
            false
        }
    }

    private fun getUserId() {
        val userId = savedStateHandle.get<Int>("userId")
        if (userId != null && userId != -1) {
            _state.update {
                it.copy(
                    userId = userId
                )
            }
        }
    }
}