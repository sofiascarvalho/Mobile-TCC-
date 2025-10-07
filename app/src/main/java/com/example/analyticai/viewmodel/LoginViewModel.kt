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

    fun login(credencial: String, senha: String) {
        viewModelScope.launch {
            if (credencial.isBlank() || senha.isBlank()) {
                _errorMessage.value = "Preencha todos os campos!"
                return@launch
            }

            try {
                val response = RetrofitFactory.getApiService().loginUsuario(LoginRequest(credencial, senha))
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.status_code == 200 && body.dados != null) {
                        _loginResponse.value = body
                    } else {
                        _errorMessage.value = body?.message ?: "Credenciais inválidas!"
                    }
                } else {
                    _errorMessage.value = "Erro ao realizar login (${response.code()})"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Falha ao conectar com o servidor"
            }
        }
    }
}
