package com.example.expensesmanager.navigation

import com.example.expensesmanager.R

data class BottomNavItem(
    val title: String,
    val route: String,
    val selectedIcon: Int
)

val listOfNavItems = listOf(
    BottomNavItem(
        title = "Budget",
        route = Screen.Budget.route,
        selectedIcon = R.drawable.moneyinvestments
    ),
    BottomNavItem(
        title = "Expenses",
        route = Screen.Expenses.route,
        selectedIcon = R.drawable.expensive
    )
)
