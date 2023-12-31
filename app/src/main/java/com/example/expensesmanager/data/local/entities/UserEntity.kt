package com.example.expensesmanager.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.expensesmanager.core.domain.util.Constants

@Entity(
    tableName = Constants.USERS_TABLE,
    indices = [Index(value = ["email"], unique = true)]
)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    val name: String,
    val email: String,
    val password: String
)
