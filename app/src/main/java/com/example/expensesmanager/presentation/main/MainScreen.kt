package com.example.expensesmanager.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.expensesmanager.navigation.Screen
import com.example.expensesmanager.presentation.main.components.MainScreenContent
import com.example.expensesmanager.presentation.main.components.MainScreenNavDrawer
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    rootNavHostController: NavHostController
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    MainScreenNavDrawer(
        modifier = Modifier.fillMaxSize(),
        username = "Daniel",
        drawerState = { drawerState },
        onMainPageClick = {
            coroutineScope.launch {
                drawerState.close()
            }
            rootNavHostController.navigate(Screen.Budget.route) {
                popUpTo(rootNavHostController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        onConverterClick = {
            //TODO: navigate to converter screen
        },
        onLogoutClick = {
            //TODO: logout user
        },
        content = {
            MainScreenContent(
                rootNavHostController = rootNavHostController,
                onOpenDrawer = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                },
                onActionClick = {
                    //TODO: navigate to action screen
                }
            )
        }
    )
}