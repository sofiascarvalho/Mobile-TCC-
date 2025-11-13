package com.example.analyticai.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.analyticai.model.Dashboard.DashboardResponse
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.analyticai.model.Login.LoginRequest
import com.example.analyticai.model.Login.LoginResponse
import com.example.analyticai.model.Login.Usuario
import com.example.analyticai.service.Conexao

class DesempenhoViewModel : ViewModel() {
    var desempenho by mutableStateOf<Usuario?>(null)
        private set

    var isLoading by mutableStateOf(true)

    fun loadPerformance(loginRequest: LoginRequest) {
        println("********************")
        viewModelScope.launch {
            isLoading = true
            try {
                val response: LoginResponse = Conexao.desempenhoService.getAluno(loginRequest)

                Log.d("DEBUG_API", "Response: $response")
                desempenho = response.usuario
            } catch (e: Exception) {
                Log.e("DEBUG_API", "Erro ao carregar desempenho", e)
                desempenho = null
            } finally {
                isLoading = false
            }
        }
    }
}
