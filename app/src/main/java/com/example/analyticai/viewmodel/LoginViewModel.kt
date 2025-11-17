package com.example.analyticai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.example.analyticai.data.repository.LoginRepository
import android.util.Log // Importação necessária
import com.example.analyticai.model.Login.LoginResponse
<<<<<<< HEAD
=======
import com.example.analyticai.service.Conexao
import com.example.analyticai.service.LoginService
>>>>>>> 20f283375523d43930bb7040e6acde64f45b9784

/**
 * ViewModel responsável pela lógica de tela de Login.
 *
 * Ele recebe o LoginRepository injetado pelo Hilt.
 * O ViewModel se comunica apenas com o Repository, que por sua vez, lida com a API e o DataStore.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    // --- Lógica de Validação (Movida para o ViewModel) ---

    fun validarCredencial(credencial: String): String? {
        return when {
            credencial.isBlank() -> "A matrícula é obrigatória."
            credencial.length < 5 -> "A credencial deve ter pelo menos 5 dígitos."
            else -> null
        }
    }

    fun validarSenha(senha: String): String? {
        return when {
            senha.isBlank() -> "A senha é obrigatória."
            senha.length < 5 -> "A senha deve ter pelo menos 6 caracteres."
            else -> null
        }
    }

    // --- Lógica de Login (Usando o Repository) ---

    /**
     * Tenta realizar o login usando o Repositório.
     *
     * @param credencial A matrícula do usuário.
     * @param senha A senha do usuário.
     * @param onSuccess Callback executado se o login for bem-sucedido e o token for salvo,
     * passando o LoginResponse para que a tela possa validar o nível de usuário.
     * @param onError Callback executado se houver falha na API ou no DataStore.
     */
    fun login(
        credencial: String,
        senha: String,
        onSuccess: (LoginResponse) -> Unit, // FIX: Agora passa o LoginResponse
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                // FIX: O repositório agora retorna o LoginResponse
                val response = loginRepository.loginUser(credencial, senha)

                // Verifica se a resposta foi bem-sucedida (status=true)
                if (response.status && response.usuario != null) {
                    // Se o login foi um sucesso E o usuário foi retornado, repassa a resposta completa.
                    onSuccess(response)
                } else {
                    // Login falhou (status=false ou usuario=null)
                    // A mensagem de erro específica poderia vir do response.message, mas estamos
                    // usando uma genérica por enquanto.
                    onError("Credenciais inválidas.")
                }

            } catch (e: Exception) {
                Log.e("LOGIN_ERROR", "Falha de Rede ou Exceção no Repositório: ", e)
                onError("Falha na conexão ou erro interno. Tente novamente.")
            }
        }
    }
}