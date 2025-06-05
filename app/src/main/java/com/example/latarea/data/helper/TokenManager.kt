package com.example.latarea.data.helper

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

object TokenManager {
    private val Context.dataStore by preferencesDataStore(name = "auth_prefs")
    private val TOKEN_KEY = stringPreferencesKey("auth_token")

    suspend fun saveToken(context: Context, token: String) {
        context.dataStore.edit { prefs -> prefs[TOKEN_KEY] = token }
    }

    suspend fun getToken(context: Context): String? {
        return context.dataStore.data.first()[TOKEN_KEY]
    }
}
