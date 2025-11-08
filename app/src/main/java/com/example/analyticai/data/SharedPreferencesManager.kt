package com.example.analyticai.data

import android.content.Context
import android.content.SharedPreferences

/**
 * Gerencia a persistência de dados simples (sessão de usuário) usando SharedPreferences.
 */
class SharedPreferencesManager(context: Context) {

    // Nome do arquivo de SharedPreferences
    private val prefs: SharedPreferences = context.getSharedPreferences(
        "Analytica_AI_Prefs", Context.MODE_PRIVATE
    )

    companion object {
        // Chaves para os dados
        private const val KEY_CREDENTIAL = "user_credential"
        private const val KEY_NIVEL = "user_nivel"
    }

    /**
     * Salva a credencial do usuário e o nível de acesso após um login bem-sucedido.
     */
    fun saveUserCredentials(credential: String, nivel: String) {
        prefs.edit().apply {
            putString(KEY_CREDENTIAL, credential)
            putString(KEY_NIVEL, nivel)
            apply() // Aplica as mudanças de forma assíncrona
        }
    }

    /**
     * Recupera a credencial salva do usuário.
     * Retorna null se não houver credencial salva.
     */
    fun getCredential(): String? {
        return prefs.getString(KEY_CREDENTIAL, null)
    }

    /**
     * Recupera o nível de acesso do usuário (aluno, professor, gestão).
     * Retorna null se não houver nível salvo.
     */
    fun getNivel(): String? {
        return prefs.getString(KEY_NIVEL, null)
    }

    /**
     * Limpa todas as credenciais para realizar o logout.
     */
    fun clearCredentials() {
        prefs.edit().apply {
            remove(KEY_CREDENTIAL)
            remove(KEY_NIVEL)
            apply()
        }
    }
}