package com.example.expensesmanager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.expensesmanager.data.local.dao.UserDao
import com.example.expensesmanager.data.local.entities.UserEntity

@Database(
    entities = [
        UserEntity::class
    ],
    version = 1
)
abstract class ExpensesDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}