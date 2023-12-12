package com.example.expensesmanager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.expensesmanager.core.presentation.util.UiEvent
import com.example.expensesmanager.presentation.login.LoginEvent
import com.example.expensesmanager.presentation.login.LoginScreen
import com.example.expensesmanager.presentation.login.LoginViewModel
import com.example.expensesmanager.presentation.main.MainScreen
import com.example.expensesmanager.presentation.register.RegisterEvent
import com.example.expensesmanager.presentation.register.RegisterScreen
import com.example.expensesmanager.presentation.register.RegisterViewModel
import com.example.expensesmanager.presentation.splash.SplashScreen
import com.example.expensesmanager.presentation.splash.SplashViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SetupNavGraph(
    setupNavController: NavHostController,
    mainNavController: NavHostController
) {
    NavHost(
        navController = setupNavController,
        startDestination = Screen.Splash.route
    ) {
        splash(
            navigateToLogin = {
                setupNavController.navigate(Screen.Login.route) {
                    popUpTo(route = Screen.Splash.route) {
                        inclusive = true
                    }
                }
            },
            navigateToMain = { userId ->
                setupNavController.navigate(Screen.Main.passUserId(userId)) {
                    popUpTo(route = Screen.Splash.route) {
                        inclusive = true
                    }
                }
            }
        )

        login(
            navigateToRegister = {
                setupNavController.navigate(Screen.Register.route) {
                    popUpTo(route = Screen.Login.route) {
                        inclusive = true
                    }
                }
            },
            navigateToMainScreen = { userId ->
                setupNavController.navigate(Screen.Main.passUserId(userId)) {
                    popUpTo(route = Screen.Login.route) {
                        inclusive = true
                    }
                }
            }
        )

        register(
            navigateToLogin = {
                setupNavController.navigate(Screen.Login.route) {
                    popUpTo(route = Screen.Register.route) {
                        inclusive = true
                    }
                }
            }
        )

        main(
            rootNavHostController = mainNavController
        )
    }
}

fun NavGraphBuilder.splash(
    navigateToLogin: () -> Unit,
    navigateToMain: (Int) -> Unit,
) {
    composable(
        route = Screen.Splash.route
    ) {

        val splashViewModel: SplashViewModel = hiltViewModel()
        val state = splashViewModel.state.collectAsState().value

        LaunchedEffect(key1 = state.onBoardSuccessfully) {
            delay(3500)
            if (state.onBoardSuccessfully && state.userId != -1) {
                navigateToMain(state.userId)
            } else {
                navigateToLogin()
            }
        }

        SplashScreen()
    }
}

fun NavGraphBuilder.login(
    navigateToRegister: () -> Unit,
    navigateToMainScreen: (Int) -> Unit
) {
    composable(
        route = Screen.Login.route
    ) {
        val loginViewModel: LoginViewModel = hiltViewModel()
        val state = loginViewModel.state.collectAsState().value

        LaunchedEffect(key1 = true) {
            loginViewModel.uiEvent.collectLatest { event ->
                when (event) {
                    is UiEvent.NavigateToMainScreen -> {
                        navigateToMainScreen(event.userId)
                    }

                    else -> Unit
                }
            }
        }

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
    navigateToLogin: () -> Unit,
) {
    composable(
        route = Screen.Register.route
    ) {
        val registerViewModel: RegisterViewModel = hiltViewModel()
        val state = registerViewModel.state.collectAsState().value

        LaunchedEffect(key1 = true) {
            registerViewModel.uiEvent.collectLatest { event ->
                when (event) {
                    UiEvent.NavigateToLogin -> {
                        navigateToLogin()
                    }

                    else -> Unit
                }
            }
        }

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

fun NavGraphBuilder.main(rootNavHostController: NavHostController) {
    composable(
        route = Screen.Main.route,
        arguments = listOf(
            navArgument(
                name = "userId"
            ) {
                type = NavType.IntType
            }
        )
    ) {
        MainScreen(rootNavHostController)
    }
}