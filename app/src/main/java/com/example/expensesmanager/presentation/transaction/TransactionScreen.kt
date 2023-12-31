package com.example.expensesmanager.presentation.transaction

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.expensesmanager.R
import com.example.expensesmanager.presentation.transaction.components.TransactionContent
import com.example.expensesmanager.ui.theme.dimens

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionScreen(
    transactionId: Int?,
    state: TransactionState,
    onEvent: (TransactionEvent) -> Unit
) {

    var canClick by remember {
        mutableStateOf(true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (transactionId != null)
                            stringResource(R.string.add_action) else stringResource(R.string.edit_action),
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            canClick = false
                            onEvent(TransactionEvent.OnBackClick)
                        },
                        enabled = canClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                actions = {
                    SaveAction(
                        onSaveClick = {
                            onEvent(TransactionEvent.OnSaveClick)
                        }
                    )
                }
            )
        }
    ) { paddingValues ->
        TransactionContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = MaterialTheme.dimens.small3,
                    end = MaterialTheme.dimens.small3
                )
                .padding(paddingValues),
            formattedDate = state.formattedDate,
            amount = state.amount,
            details = state.details,
            selectedCategory = state.category,
            onDateTimeChange = { zonedDateTime ->
                onEvent(TransactionEvent.OnDateTimeChange(zonedDateTime))
            },
            onAmountChange = { amount ->
                onEvent(TransactionEvent.OnAmountChange(amount))
            },
            onCategoryChange = { category ->
                onEvent(TransactionEvent.OnCategoryChange(category))
            },
            onDetailsChange = { details ->
                onEvent(TransactionEvent.OnDetailsChange(details))
            },
            imageBytes = state.imageBytes,
            onPictureTaken = { imageUri ->
                imageUri?.let { uri ->
                    onEvent(TransactionEvent.OnPictureTaken(uri))
                }
            },
            onClearImage = {
                onEvent(TransactionEvent.ClearImage)
            }
        )
    }
}

@Composable
private fun SaveAction(
    onSaveClick: () -> Unit
) {
    TextButton(
        onClick = onSaveClick
    ) {
        Text(
            text = stringResource(id = R.string.save),
            style = TextStyle(
                fontWeight = FontWeight.SemiBold
            ),
            color = MaterialTheme.colorScheme.primary
        )
    }
}