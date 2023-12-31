package com.example.expensesmanager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.expensesmanager.data.local.dao.TransactionDao
import com.example.expensesmanager.data.local.dao.UserDao
import com.example.expensesmanager.data.local.entities.TransactionEntity
import com.example.expensesmanager.data.local.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,
        TransactionEntity::class
    ],
    version = 1
)
abstract class ExpensesDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun transactionDao(): TransactionDao
}