package com.example.expensesmanager.navigation

sealed class Screen(val route: String) {

    object Splash : Screen("splash_screen")
    object Login : Screen("login_screen")
    object Register : Screen("register_screen")
    object Main : Screen("main_screen/{userId}") {
        fun passUserId(userId: Int) = "main_screen/$userId"
    }

    object Budget : Screen("budget_screen"){
        fun passUserId(userId: Int) = "budget_screen/$userId"
    }
    object Expenses : Screen("expenses_screen"){
        fun passUserId(userId: Int) = "expenses_screen/$userId"
    }

}