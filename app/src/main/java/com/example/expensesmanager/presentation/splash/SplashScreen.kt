package com.example.expensesmanager.presentation.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.expensesmanager.R
import com.example.expensesmanager.ui.theme.dimens

@Composable
fun SplashScreen() {

    var startAnimation by remember {
        mutableStateOf(false)
    }

    val fadeInAnimation by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        label = "",
        animationSpec = tween(
            durationMillis = 2000
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.alpha(fadeInAnimation).size(MaterialTheme.dimens.logoLarge),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = stringResource(R.string.logo)
        )

        Spacer(modifier = Modifier.height(MaterialTheme.dimens.medium1))

        Text(
            modifier = Modifier.alpha(fadeInAnimation),
            text = stringResource(R.string.spend_with_brain),
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize
            ),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}