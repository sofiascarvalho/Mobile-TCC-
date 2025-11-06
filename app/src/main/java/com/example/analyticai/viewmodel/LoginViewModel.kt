package com.example.analyticai.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
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
import okhttp3.Response

class LoginViewModel : ViewModel() {

/*
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
*/
class LoginViewModel : ViewModel() {

    fun login(
        email: String,
        senha: String,
        onSuccess: (User) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response: Response<User> = RetrofitFactory.userService.loginUser(LoginRequest(email, senha))

                if (response.isSuccessful && response.body() != null) {
                    onSuccess(response.body()!!)
                } else {
                    onError("Email ou senha incorretos!")
                }
            } catch (e: Exception) {
                onError("Erro de conexão: ${e.localizedMessage}")
            }
        }
    }
}

}
