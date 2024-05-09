package com.example.braguia.repositories

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class PreferencesRepository(private val dataStore: DataStore<Preferences>) {

    private companion object {
        val IS_DARK_THEME = booleanPreferencesKey("is_linear_layout")
        val ASK_AGAIN = booleanPreferencesKey("ask_again")
        const val TAG = "UserPreferencesRepo"
    }

    suspend fun resetPreferences() {
        dataStore.edit { preferences ->
            preferences[IS_DARK_THEME] = false
            preferences[ASK_AGAIN] = true
        }
    }

    suspend fun setThemePreference(isLinearLayout: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_THEME] = isLinearLayout
        }
    }

    suspend fun setGoogleMapsAskAgain(askAgain: Boolean) {
        dataStore.edit { preferences -> preferences[ASK_AGAIN] = askAgain }
    }

    val getIsDarkTeme: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[IS_DARK_THEME] ?: false
        }

    val getIsAskAgain: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[ASK_AGAIN] ?: true
        }


}