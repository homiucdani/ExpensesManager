package com.example.expensesmanager.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import com.example.expensesmanager.R
import com.example.expensesmanager.ui.theme.dimens

@Composable
fun DetailsSection(
    actionName: String,
    question: String,
    showForgotPassword: Boolean,
    onForgotPasswordClick: () -> Unit,
    onActionClick: () -> Unit,
    onButtonEnabled: Boolean,
    onButtonEnabledChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (showForgotPassword) {
            Text(
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource()
                ) {
                    onForgotPasswordClick()
                },
                text = stringResource(R.string.forgot_your_password),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                ),
                color = MaterialTheme.colorScheme.onPrimary,
                textDecoration = TextDecoration.Underline
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small3))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = question)

            Spacer(modifier = Modifier.width(MaterialTheme.dimens.medium2))

            Text(
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = MutableInteractionSource(),
                    enabled = onButtonEnabled
                ) {
                    onButtonEnabledChange(false)
                    onActionClick()
                },
                text = actionName,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                ),
                color = MaterialTheme.colorScheme.onPrimary,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}