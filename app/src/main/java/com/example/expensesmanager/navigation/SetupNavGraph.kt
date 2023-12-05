package com.example.expensesmanager.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.expensesmanager.presentation.splash.SplashScreen
import kotlinx.coroutines.delay

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        splash {
            navController.navigate(Screen.Login.route) {
                popUpTo(route = Screen.Splash.route) {
                    inclusive = true
                }
            }
        }
        login()
    }
}

fun NavGraphBuilder.splash(
    navigateToLogin: () -> Unit
) {
    composable(
        route = Screen.Splash.route
    ) {

        LaunchedEffect(key1 = true) {
            delay(3500)
            navigateToLogin()
        }

        SplashScreen()
    }
}

fun NavGraphBuilder.login() {
    composable(
        route = Screen.Login.route
    ) {

        Text(text = "Login screen")
    }
}