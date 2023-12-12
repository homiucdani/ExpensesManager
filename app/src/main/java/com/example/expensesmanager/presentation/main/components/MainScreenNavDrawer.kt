package com.example.expensesmanager.presentation.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.expensesmanager.R
import com.example.expensesmanager.ui.theme.dimens

@Composable
fun MainScreenNavDrawer(
    modifier: Modifier = Modifier,
    username: String,
    isLoggingOff: Boolean,
    drawerState: () -> DrawerState,
    onMainPageClick: () -> Unit,
    onConverterClick: () -> Unit,
    onLogoutClick: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState(),
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.2f)
                        .background(color = MaterialTheme.colorScheme.primary)
                        .padding(
                            horizontal = MaterialTheme.dimens.medium1,
                            vertical = MaterialTheme.dimens.medium2
                        ),
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(MaterialTheme.dimens.large),
                        painter = painterResource(id = R.drawable.account_icon),
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))

                    Text(
                        text = "Hello $username",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize
                        ),
                        textDecoration = TextDecoration.Underline
                    )
                }

                Divider(thickness = 1.dp, modifier = Modifier.fillMaxWidth())

                NavigationDrawerItem(
                    label = {
                        Text(
                            text = stringResource(R.string.main_page),
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    selected = false,
                    onClick = {
                        onMainPageClick()
                    }
                )

                Divider(thickness = 1.dp, modifier = Modifier.fillMaxWidth())

                NavigationDrawerItem(
                    label = {
                        Text(
                            text = stringResource(R.string.converter),
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    selected = false,
                    onClick = {
                        onConverterClick()
                    }
                )

                Divider(thickness = 1.dp, modifier = Modifier.fillMaxWidth())

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            bottom = MaterialTheme.dimens.medium3,
                            start = MaterialTheme.dimens.small1
                        )
                ) {
                    NavigationDrawerItem(
                        modifier = Modifier.align(Alignment.BottomStart),
                        label = {
                            Text(
                                text = stringResource(R.string.logout),
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                fontWeight = FontWeight.SemiBold
                            )
                        },
                        selected = false,
                        onClick = {
                            if (!isLoggingOff){
                                onLogoutClick()
                            }
                        }
                    )
                }
            }
        },
        content = content
    )
}