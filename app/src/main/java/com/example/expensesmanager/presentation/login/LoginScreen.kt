package com.example.expensesmanager.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.example.expensesmanager.R
import com.example.expensesmanager.core.presentation.components.DetailsSection
import com.example.expensesmanager.ui.theme.dimens

@Composable
fun LoginScreen(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit
) {

    var passwordVisibility by remember {
        mutableStateOf(true)
    }

    val passIcon =
        if (passwordVisibility) R.drawable.ic_key_visible else R.drawable.ic_key_invisible

    var canClick by remember {
        mutableStateOf(true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(top = MaterialTheme.dimens.large)
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(MaterialTheme.dimens.logoMedium),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium3))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = MaterialTheme.dimens.medium2),
            text = stringResource(R.string.enter_your_credentials_to_become_again_active),
            style = TextStyle(
                fontWeight = FontWeight.Light,
                fontSize = MaterialTheme.typography.titleMedium.fontSize
            ),
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium3))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.medium2),
            value = state.email,
            onValueChange = { email ->
                onEvent(LoginEvent.OnEmailChange(email))
            },
            label = {
                Text(text = stringResource(R.string.email))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            shape = RoundedCornerShape(MaterialTheme.dimens.small1)
        )

        if (state.emailError != null) {
            Text(
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.medium2),
                text = state.emailError,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                ),
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium2))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.dimens.medium2),
            value = state.password,
            onValueChange = { password ->
                onEvent(LoginEvent.OnPasswordChange(password))
            },
            label = {
                Text(text = stringResource(R.string.password))
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        passwordVisibility = !passwordVisibility
                    }
                ) {
                    Icon(painter = painterResource(id = passIcon), contentDescription = null)
                }
            },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            shape = RoundedCornerShape(MaterialTheme.dimens.small1)
        )

        if (state.passwordError != null) {
            Text(
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.medium2),
                text = state.passwordError,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                ),
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium3))

        Button(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.5f),
            onClick = {
                onEvent(LoginEvent.OnLoginClick)
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSecondaryContainer)
        ) {
            Text(text = stringResource(id = R.string.login))
        }

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium3))

        DetailsSection(
            actionName = stringResource(id = R.string.register),
            question = stringResource(id = R.string.have_no_account),
            onForgotPasswordClick = {

            },
            onActionClick = {
                onEvent(LoginEvent.NavigateToRegister)
            },
            showForgotPassword = true,
            onButtonEnabled = canClick,
            onButtonEnabledChange = { disableButton ->
                canClick = disableButton
            }
        )
    }
}
