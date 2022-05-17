package com.mamisiaga.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mamisiaga.`class`.Ibu
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class IbuPreference private constructor(private val dataStore: DataStore<Preferences>) {
    fun getIbu(): Flow<Ibu> {
        return dataStore.data.map { preferences ->
            Ibu(
                preferences[NAME_KEY] ?: "",
                preferences[EMAIL_KEY] ?: "",
                preferences[ID_KEY] ?: "",
                //preferences[DATE_OF_BIRTH_KEY] ?: "",
                //preferences[TYPE_KEY] ?: "",
                //preferences[IS_PARTICIPATING_POSYANDU_KEY] ?: false,
                preferences[STATE_KEY] ?: false
            )
        }
    }

    suspend fun masuk(ibu: Ibu) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = ibu.name
            preferences[EMAIL_KEY] = ibu.email
            preferences[ID_KEY] = ibu.id
            //preferences[DATE_OF_BIRTH_KEY] = ibu.dateOfBirth
            //preferences[TYPE_KEY] = ibu.type
            //preferences[IS_PARTICIPATING_POSYANDU_KEY] = ibu.isParticipatingPosyandu
            preferences[STATE_KEY] = ibu.isMasuk
        }
    }

    suspend fun keluar() {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = ""
            preferences[EMAIL_KEY] = ""
            preferences[ID_KEY] = ""
            //preferences[DATE_OF_BIRTH_KEY] = ""
            //preferences[TYPE_KEY] = ""
            //preferences[IS_PARTICIPATING_POSYANDU_KEY] = false
            preferences[STATE_KEY] = false
        }
    }

    companion object {
        @Volatile
        var INSTANCE: IbuPreference? = null

        val NAME_KEY = stringPreferencesKey("name")
        val EMAIL_KEY = stringPreferencesKey("email")
        val ID_KEY = stringPreferencesKey("id")
        val DATE_OF_BIRTH_KEY = stringPreferencesKey("dateOfBirth")
        val TYPE_KEY = stringPreferencesKey("type")
        val IS_PARTICIPATING_POSYANDU_KEY = booleanPreferencesKey("isParticipatingPosyandu")
        val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): IbuPreference =
            INSTANCE ?: synchronized(this) {
                val instance = IbuPreference(dataStore)
                INSTANCE = instance
                instance
            }
    }
}
