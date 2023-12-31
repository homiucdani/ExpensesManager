package com.example.expensesmanager.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.example.expensesmanager.domain.model.TransactionCategory

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = CASCADE
        )
    ]
)
data class TransactionEntity(
    val userId: Int,
    @PrimaryKey(autoGenerate = true)
    val transactionId: Long = 0,
    val transactionDate: Long,
    val transactionAmount: Double,
    val transactionCategory: TransactionCategory,
    val transactionDetails: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val transactionImage: ByteArray?
)
