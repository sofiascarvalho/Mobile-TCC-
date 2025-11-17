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

    var desempenho by mutableStateOf<DashboardResponse?>(null)
        private set

    var isLoading by mutableStateOf(true)

    fun loadPerformance(
        idAluno: Int,
        idMateria: Int? = null,
        idSemestre: Int? = null
    ) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = Conexao.desempenhoService.getDesempenho(
                    idAluno = idAluno,
                    idMateria = idMateria,
                    idSemestre = idSemestre
                )
                desempenho = response
            } catch (e: Exception) {
                Log.e("DEBUG_API", "Erro ao carregar desempenho", e)
                desempenho = null
            } finally {
                isLoading = false
            }
        }
    }
}
