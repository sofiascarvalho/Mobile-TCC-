package com.example.analyticai.data

import android.content.Context
import android.content.SharedPreferences
import com.example.analyticai.model.Login.Usuario
import com.google.gson.Gson

class SharedPreferencesManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        "Analytica_AI_Prefs", Context.MODE_PRIVATE
    )

    companion object {
        private const val KEY_CREDENTIAL = "user_credential"
        private const val KEY_NIVEL = "user_nivel"
        private const val KEY_USUARIO_JSON = "user_json" // NOVO
    }

    fun saveUserCredentials(credential: String, nivel: String) {
        prefs.edit().apply {
            putString(KEY_CREDENTIAL, credential)
            putString(KEY_NIVEL, nivel)
            apply()
        }
    }

    fun getCredential(): String? = prefs.getString(KEY_CREDENTIAL, null)

    fun getNivel(): String? = prefs.getString(KEY_NIVEL, null)

    fun clearCredentials() {
        prefs.edit().apply {
            remove(KEY_CREDENTIAL)
            remove(KEY_NIVEL)
            remove(KEY_USUARIO_JSON)
            apply()
        }
    }

    // --- NOVO: salvar e recuperar usuário completo ---
    fun saveUsuario(usuario: Usuario) {
        val json = Gson().toJson(usuario)
        prefs.edit().putString(KEY_USUARIO_JSON, json).apply()
    }

    fun getUsuario(): Usuario? {
        val json = prefs.getString(KEY_USUARIO_JSON, null)
        return json?.let { Gson().fromJson(it, Usuario::class.java) }
    }
}
