package com.example.analyticai.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("user_prefs")

class UserPreferences(private val context: Context) {
    companion object{
        val CREDENCIAL = stringPreferencesKey("credencial")
    }
    val credencialFlow: Flow<String?> = context.dataStore.data.map { prefs->
        prefs[CREDENCIAL]
    }
    suspend fun salvarCredencial(credencial: String){
        context.dataStore.edit { prefs ->
            prefs[CREDENCIAL] = credencial
        }
    }
    suspend fun limparDados(){
        context.dataStore.edit { it.clear() }
    }
}