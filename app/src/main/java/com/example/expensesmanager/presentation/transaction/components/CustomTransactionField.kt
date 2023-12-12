package com.example.expensesmanager.presentation.transaction.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun CustomTransactionField(
    title: String,
    text: String,
    onTextChange: (String) -> Unit,
    hint: String
) {
    Column {
        Text(
            text = title,
            style = TextStyle(
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Normal
            ),
            color = MaterialTheme.colorScheme.onBackground
        )

        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            placeholder = {
                Text(text = hint)
            }
        )
    }
}