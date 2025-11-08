package com.example.analyticai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import android.util.Log // ⬅️ Importação necessária

import com.example.analyticai.model.Login.LoginRequest
import com.example.analyticai.model.Login.LoginResponse
import com.example.analyticai.service.LoginService
import com.example.analyticai.service.Conexao

class LoginViewModel(
    private val loginService: LoginService = Conexao.loginService
) : ViewModel() {

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
            senha.length < 6 -> "A senha deve ter pelo menos 6 caracteres."
            else -> null
        }
    }

    fun login(
        credencial: String,
        senha: String,
        onSuccess: (LoginResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(credencial = credencial, senha = senha)
                val response: Response<LoginResponse> = loginService.login(request)

                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.status == true && body.usuario != null) {
                        onSuccess(body)
                    } else if (body?.status == false) {
                        onError("Credenciais inválidas. Tente novamente.")
                    } else {
                        onError("Erro desconhecido na resposta do servidor.")
                    }
                } else {
                    val code = response.code()
                    val errorBody = response.errorBody()?.string()

                    val mensagemErro = when (code) {
                        400 -> "Requisição inválida. Verifique os dados enviados."
                        401 -> "Credenciais incorretas. Verifique seu login e senha."
                        403 -> "Acesso negado. Permissões insuficientes."
                        404 -> "Usuário não encontrado. Verifique sua credencial."
                        415 -> "Erro de formato. O servidor só aceita JSON."
                        500 -> "Erro interno no servidor. Tente novamente mais tarde."
                        502, 503, 504 -> "Servidor temporariamente indisponível. Tente mais tarde."
                        else -> "Erro inesperado (${code}): ${errorBody ?: "Sem detalhes"}"
                    }

                    Log.e("LOGIN_API_ERROR", "Erro HTTP $code: $errorBody")
                    onError(mensagemErro)
                }

            } catch (e: Exception) {
                Log.e("LOGIN_API_ERROR", "Falha de Rede (Exceção Capturada): ", e)
                onError("Falha na conexão: Verifique sua internet ou tente novamente mais tarde.")
            }
        }
    }

    companion object {
        fun provideFactory(
            loginService: LoginService = Conexao.loginService
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoginViewModel(loginService) as T
            }
        }
    }
}