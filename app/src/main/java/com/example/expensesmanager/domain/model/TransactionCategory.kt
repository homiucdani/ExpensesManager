package com.example.expensesmanager.domain.model

import androidx.compose.ui.graphics.Color
import com.example.expensesmanager.R
import com.example.expensesmanager.ui.theme.DefaultGreen
import com.example.expensesmanager.ui.theme.DefaultOrange

enum class TransactionCategory(
    val icon: Int,
    val defaultColor: Color = DefaultGreen,
    val selectedColor: Color = DefaultOrange
) {
    Income(
        icon = R.drawable.income
    ),
    Food(
        icon = R.drawable.food
    ),
    Car(
        icon = R.drawable.car
    ),
    Clothes(
        icon = R.drawable.clothes
    ),
    Savings(
        icon = R.drawable.savings
    ),
    Health(
        icon = R.drawable.health
    ),
    Beauty(
        icon = R.drawable.beauty
    ),
    Travel(
        icon = R.drawable.travel
    );
}