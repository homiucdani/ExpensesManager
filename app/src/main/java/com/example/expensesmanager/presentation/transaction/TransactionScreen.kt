package com.example.expensesmanager.presentation.transaction

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionScreen(
    transactionId: Int?
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = if (transactionId != null) "Add action" else "Edit action")
                }
            )
        },

    ) { paddingValues ->

    }
}