package com.example.analyticai.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.analyticai.data.UserPreferences
import com.example.analyticai.model.LoginRequest
import com.example.analyticai.model.LoginResponse
import com.example.analyticai.model.Usuario
import com.example.analyticai.screens.LoginScreen
import com.example.analyticai.service.RetrofitFactory
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val context: Context) : ViewModel() {

    private val userPreferences = UserPreferences(context)


    private val _loginResponse = MutableStateFlow<LoginResponse?>(null)
    val loginResponse: StateFlow<LoginResponse?> = _loginResponse

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    fun validarCredencial(credencial: String): String?{
        return when{
            credencial.length<2 -> "A credencial deve ter no mínimo 2 caracteres"
            credencial.length>11 -> "A  credencial deve ter no máximo 11 caracteres"
            else-> null
        }
    }

    fun validarSenha(senha: String): String?{
        return when{
            senha.length<8 -> "A senha deve ter no mínimo 8 caracteres"
            senha.length>20 -> "a senha deve ter até 20 caracteres"
            else->null
        }
    }

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

            try {
                val response = RetrofitFactory.getApiService().loginUsuario(
                    LoginRequest(credencial, senha)
                )
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status_code in listOf(200, 201)) {
                        _loginResponse.value = body
                        salvarLogin(body)
                        onSuccess(body)
                    } else {
                        val msg = body?.message ?: "Credenciais inválidas!"
                        _errorMessage.value = msg
                        onError(msg)
                    }
                } else {
                    val msg = "Erro ao realizar login (${response.code()})"
                    _errorMessage.value=msg
                    onError(msg)
                }
            } catch (e: Exception) {
                val msg = "Falha ao conectar: ${e.message}"
                _errorMessage.value = msg
                onError(msg)
                e.printStackTrace()
            }
        }
    }

    fun salvarLogin(loginResponse: LoginResponse) {
        viewModelScope.launch {
            loginResponse.dados?.firstOrNull()?.let { usuario ->
                userPreferences.salvarCredencial(credencial = "")
            }
        }
    }

    fun usuarioFlow(): Flow<String?> = userPreferences.credencialFlow

    companion object {
        fun provideFactory(context: Context): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                        return LoginViewModel(context) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }

}
