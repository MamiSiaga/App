package com.mamisiaga.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mamisiaga.dataClass.LoginModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LoginPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getLogin(): Flow<LoginModel> {
        return dataStore.data.map { preferences ->
            LoginModel(
                preferences[TOKEN_KEY] ?: "",
                preferences[STATE_KEY] ?: false
            )
        }
    }

    suspend fun saveLogin(user: LoginModel){
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = user.token
            preferences[STATE_KEY] = user.isMasuk
        }
    }

    suspend fun logout(){
        dataStore.edit { preferences ->
            preferences[STATE_KEY] = false
            preferences[TOKEN_KEY] = ""
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: LoginPreference? = null

        private val TOKEN_KEY = stringPreferencesKey("token")
        private val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): LoginPreference {
            return INSTANCE ?: synchronized(this){
                val instance = LoginPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}