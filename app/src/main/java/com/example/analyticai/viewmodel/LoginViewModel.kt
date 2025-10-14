package com.example.analyticai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.analyticai.model.LoginRequest
import com.example.analyticai.model.LoginResponse
import com.example.analyticai.service.RetrofitFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginResponse = MutableStateFlow<LoginResponse?>(null)
    val loginResponse: StateFlow<LoginResponse?> = _loginResponse

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun login(
        credencial: String,
        senha: String,
        onSuccess: (LoginResponse) -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
            if (credencial.isBlank() || senha.isBlank()) {
                onError("Preencha todos os campos!")
                return@launch
            }

            _isLoading.value = true  // ⬅️ Início do carregamento

            try {
                val response = RetrofitFactory.getApiService().loginUsuario(
                    LoginRequest(credencial, senha)
                )
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status_code in listOf(200, 201)) {
                        _loginResponse.value = body
                        onSuccess(body)
                    } else {
                        val msg = body?.message ?: "Credenciais inválidas!"
                        _errorMessage.value = msg
                        onError(msg)
                    }
                } else {
                    val msg = "Erro ao realizar login (${response.code()})"
                    _errorMessage.value = msg
                    onError(msg)
                }
            } catch (e: Exception) {
                val msg = "Falha ao conectar: ${e.message}"
                _errorMessage.value = msg
                onError(msg)
                e.printStackTrace()
            } finally {
                _isLoading.value = false  // ⬅️ Fim do carregamento
            }
        }
    }
}
