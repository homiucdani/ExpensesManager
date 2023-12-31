package com.example.expensesmanager.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.expensesmanager.data.local.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(transactionEntity: TransactionEntity)

    @Query("SELECT * FROM transactions WHERE userId = :userId")
    fun getAllTransaction(userId: Int): Flow<List<TransactionEntity>>
}