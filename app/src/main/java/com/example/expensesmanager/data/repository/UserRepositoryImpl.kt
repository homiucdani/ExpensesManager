package com.example.expensesmanager.data.repository

import com.example.expensesmanager.data.local.dao.UserDao
import com.example.expensesmanager.data.mapper.toUser
import com.example.expensesmanager.data.mapper.toUserEntity
import com.example.expensesmanager.domain.model.User
import com.example.expensesmanager.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun insertUser(userEntity: User) {
        userDao.insertUser(userEntity.toUserEntity())
    }

    override suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)?.toUser()
    }

    override suspend fun getUserById(id: Int): User {
        return userDao.getUserById(id).toUser()
    }

    override suspend fun login(email: String, password: String): User? {
        return userDao.login(email, password)?.toUser()
    }

    override suspend fun deleteUserById(id: Int) {
        return userDao.deleteUserById(id)
    }
}