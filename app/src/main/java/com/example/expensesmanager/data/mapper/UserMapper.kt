package com.example.expensesmanager.data.mapper

import com.example.expensesmanager.data.local.entities.UserEntity
import com.example.expensesmanager.domain.model.User

fun UserEntity.toUser(): User {
    return User(
        userId, name, email, password
    )
}

fun User.toUserEntity(): UserEntity {
    return UserEntity(
        userId, name, email, password
    )
}
