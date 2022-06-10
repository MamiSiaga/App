package com.mamisiaga.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.mamisiaga.dataClass.Ibu
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class IbuPreference private constructor(private val dataStore: DataStore<Preferences>) {
    fun getIbu(): Flow<Ibu> {
        return dataStore.data.map { preferences ->
            Ibu(
                preferences[ID_KEY] ?: -1,
                preferences[TOKEN_KEY] ?: "",
                preferences[STATE_KEY] ?: false
            )
        }
    }

    suspend fun masuk(ibu: Ibu) {
        dataStore.edit { preferences ->
            preferences[ID_KEY] = ibu.id!!
            preferences[TOKEN_KEY] = ibu.token!!
            preferences[STATE_KEY] = ibu.isMasuk!!
        }
    }

    suspend fun keluar() {
        dataStore.edit { preferences ->
            preferences[ID_KEY] = -1
            preferences[TOKEN_KEY] = ""
            preferences[STATE_KEY] = false
        }
    }

    companion object {
        @Volatile
        var INSTANCE: IbuPreference? = null
        val ID_KEY = intPreferencesKey("id")
        val TOKEN_KEY = stringPreferencesKey("token")
        val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): IbuPreference =
            INSTANCE ?: synchronized(this) {
                val instance = IbuPreference(dataStore)
                INSTANCE = instance
                instance
            }
    }
}
