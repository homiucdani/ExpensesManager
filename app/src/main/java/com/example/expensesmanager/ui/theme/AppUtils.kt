package com.example.expensesmanager.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember

@Composable
fun AppUtils(
    appDimens: Dimens,
    content: @Composable () -> Unit
) {
    val appDimens = remember {
        appDimens
    }
    // affect the content inside
    CompositionLocalProvider(LocalAppDimens provides appDimens) {
        content()
    }
}
// provide an object to compose
val LocalAppDimens = compositionLocalOf {
    CompactStandardDimens
}