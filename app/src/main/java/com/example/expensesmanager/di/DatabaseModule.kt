package com.example.expensesmanager.di

import android.content.Context
import androidx.room.Room
import com.example.expensesmanager.core.domain.util.Constants
import com.example.expensesmanager.data.local.ExpensesDatabase
import com.example.expensesmanager.data.local.dao.UserDao
import com.example.expensesmanager.data.repository.UserRepositoryImpl
import com.example.expensesmanager.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesExpensesDatabase(@ApplicationContext context: Context): ExpensesDatabase {
        return Room.databaseBuilder(
            context,
            ExpensesDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesUserDao(
        expensesDatabase: ExpensesDatabase
    ): UserDao {
        return expensesDatabase.userDao()
    }

    @Provides
    @Singleton
    fun providesUserRepository(
        userDao: UserDao
    ): UserRepository {
        return UserRepositoryImpl(userDao)
    }
}