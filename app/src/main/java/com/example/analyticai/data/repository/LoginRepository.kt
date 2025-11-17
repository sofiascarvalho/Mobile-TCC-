package com.example.analyticai.data.repository

import com.example.analyticai.service.LoginService
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import com.example.analyticai.model.Login.LoginResponse
import com.example.analyticai.model.Login.LoginRequest
import com.example.analyticai.model.Login.Usuario
import com.example.analyticai.model.Login.Turma

/**
 * Repositório responsável por orquestrar as operações de Login.
 * Ele interage com o LoginService (API) e o DataStore (Persistência Local).
 *
 * O Hilt usa a anotação @Inject no construtor para fornecer as dependências.
 */
class LoginRepository @Inject constructor(
    private val loginService: LoginService,
    private val userPreferencesStore: DataStore<Preferences>
) {
    private val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")

    suspend fun loginUser(credencial: String, password: String): LoginResponse {

        // 1. Criar o objeto de requisição
        val requestBody = LoginRequest(credencial, password)

        // 2. Chamar a API de Login (AGORA É REAL)
        val apiResponse = loginService.login(requestBody)

        // 3. Checar a resposta da API
        if (apiResponse.isSuccessful) {
            // O corpo da resposta (body) é a LoginResponse
            val responseBody = apiResponse.body()

            // Certifique-se de que o corpo não é nulo e o login foi um sucesso
            if (responseBody != null && responseBody.status) {
                // Se sua API retornar o token no corpo, você pode salvá-lo aqui.
                // Exemplo (se a API retornar um campo 'token' no LoginResponse):
                // responseBody.token?.let { saveAuthToken(it) }

                // Retorna a resposta real da API
                return responseBody
            }
        }

        // Se a chamada falhar (ex: 401, 404, etc.) ou o corpo for nulo/status=false
        // Retorna uma LoginResponse de falha para que o ViewModel possa tratar.
        return LoginResponse(
            status = false,
            status_code = apiResponse.code(), // Usa o código HTTP real
            usuario = null
        )
    }
    /**
     * Recupera o token de autenticação do DataStore (Exemplo de uso).
     */
    suspend fun getAuthToken(): String? {
        val preferences = userPreferencesStore.data.first()
        return preferences[AUTH_TOKEN_KEY]
    }

    // Adicione outras funções relacionadas a autenticação (logout, refresh token, etc.)
}