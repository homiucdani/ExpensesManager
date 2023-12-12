package com.example.expensesmanager.presentation.main.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.expensesmanager.R
import com.example.expensesmanager.navigation.Screen
import com.example.expensesmanager.navigation.listOfNavItems
import com.example.expensesmanager.presentation.main.budget.BudgetScreen
import com.example.expensesmanager.presentation.main.expenses.ExpensesScreen
import com.example.expensesmanager.ui.theme.dimens

@Composable
fun MainScreenContent(
    rootNavHostController: NavHostController,
    onOpenDrawer: () -> Unit,
    onActionClick: () -> Unit
) {
    val backStackEntry by rootNavHostController.currentBackStackEntryAsState()

    Scaffold(
        topBar = {
            MainTopAppBar(
                title = if (backStackEntry?.destination?.route == Screen.Budget.route) stringResource(
                    id = R.string.my_budget
                ) else stringResource(R.string.my_expenses),
                onOpenDrawer = onOpenDrawer,
                onActionClick = onActionClick
            )
        },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.clip(
                    RoundedCornerShape(
                        topEnd = MaterialTheme.dimens.medium1,
                        topStart = MaterialTheme.dimens.medium1
                    )
                ),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                listOfNavItems.forEachIndexed { index, bottomNavItem ->
                    val isSelected = bottomNavItem.route == backStackEntry?.destination?.route

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            rootNavHostController.navigate(bottomNavItem.route) {
                                popUpTo(rootNavHostController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                modifier = Modifier.size(MaterialTheme.dimens.medium1),
                                painter = painterResource(id = bottomNavItem.selectedIcon),
                                contentDescription = null
                            )
                        },
                        label = {
                            Text(text = bottomNavItem.title)
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = rootNavHostController,
            startDestination = Screen.Budget.route
        ) {
            composable(
                route = Screen.Budget.route
            ) {
                BudgetScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }

            composable(route = Screen.Expenses.route) {
                ExpensesScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }
        }
    }
}