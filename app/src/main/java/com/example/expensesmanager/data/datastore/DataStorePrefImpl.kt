package com.example.expensesmanager.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.expensesmanager.core.domain.util.Constants
import com.example.expensesmanager.domain.model.UserOnBoard
import com.example.expensesmanager.domain.repository.DataStorePref
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStorePrefImpl(
    private val dataStore: DataStore<Preferences>
) : DataStorePref {

    companion object PreferencesKey {
        val onBoardSuccessfullyKey = booleanPreferencesKey(Constants.ON_BOARD_SUCCESSFULLY_KEY)
        val userIdKey = intPreferencesKey(Constants.USER_ID_KEY)
    }

    override suspend fun saveUserOnBoard(userOnBoard: UserOnBoard) {
        dataStore.edit { pref ->
            pref[PreferencesKey.onBoardSuccessfullyKey] = userOnBoard.onBoardSuccessfully
            pref[PreferencesKey.userIdKey] = userOnBoard.userId
        }
    }

    override fun getUserOnBoard(): Flow<UserOnBoard> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw Exception()
            }
        }.map { pref ->
            val onBoardSuccessfully = pref[PreferencesKey.onBoardSuccessfullyKey] ?: false
            val userId = pref[PreferencesKey.userIdKey] ?: -1
            UserOnBoard(onBoardSuccessfully, userId)
        }
    }
}