package com.example.expensesmanager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.expensesmanager.presentation.login.LoginEvent
import com.example.expensesmanager.presentation.login.LoginScreen
import com.example.expensesmanager.presentation.login.LoginViewModel
import com.example.expensesmanager.presentation.register.RegisterEvent
import com.example.expensesmanager.presentation.register.RegisterScreen
import com.example.expensesmanager.presentation.register.RegisterViewModel
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
        login(
            navigateToRegister = {
                navController.navigate(Screen.Register.route) {
                    popUpTo(route = Screen.Login.route) {
                        inclusive = true
                    }
                }
            }
        )

        register(
            navigateToLogin = {
                navController.navigate(Screen.Login.route) {
                    popUpTo(route = Screen.Register.route) {
                        inclusive = true
                    }
                }
            }
        )
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

fun NavGraphBuilder.login(
    navigateToRegister: () -> Unit
) {
    composable(
        route = Screen.Login.route
    ) {
        val loginViewModel: LoginViewModel = hiltViewModel()
        val state = loginViewModel.state.collectAsState().value

        LoginScreen(
            state = state,
            onEvent = { event ->
                when (event) {
                    LoginEvent.NavigateToRegister -> {
                        navigateToRegister()
                    }

                    else -> loginViewModel.onEvent(event)
                }
            }
        )
    }
}

fun NavGraphBuilder.register(
    navigateToLogin: () -> Unit
) {
    composable(
        route = Screen.Register.route
    ) {
        val registerViewModel: RegisterViewModel = viewModel()
        val state = registerViewModel.state.collectAsState().value

        RegisterScreen(
            state = state,
            onEvent = { event ->
                when (event) {
                    RegisterEvent.NavigateToLogin -> {
                        navigateToLogin()
                    }

                    else -> registerViewModel.onEvent(event)
                }
            }
        )
    }
}