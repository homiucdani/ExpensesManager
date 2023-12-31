package com.example.expensesmanager.presentation.transaction.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.expensesmanager.domain.model.TransactionCategory
import com.example.expensesmanager.ui.theme.dimens

@Composable
fun CategoryItem(
    transactionCategory: TransactionCategory,
    selectedCategory: TransactionCategory,
    onCategoryClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(5.dp))
            .aspectRatio(1f)
            .border(
                width = 1.5.dp,
                color = if (transactionCategory == selectedCategory)
                    transactionCategory.selectedColor else transactionCategory.defaultColor,
                shape = RoundedCornerShape(5.dp)
            )
            .clickable {
                onCategoryClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = transactionCategory.icon),
                contentDescription = transactionCategory.name
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.extraSmall))
            Text(
                text = transactionCategory.name,
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = MaterialTheme.typography.labelMedium.fontSize
                )
            )
        }
    }
}