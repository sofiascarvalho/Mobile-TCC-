package com.example.analyticai.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.analyticai.model.Dashboard.DashboardResponse
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
// Importações de Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow // Necessário para expor o StateFlow de forma segura

// Imports de Serviço (assumindo que estas são as corretas)
import com.example.analyticai.model.Login.LoginRequest
import com.example.analyticai.model.Login.LoginResponse
import com.example.analyticai.model.Login.Usuario
import com.example.analyticai.service.Conexao

class DesempenhoViewModel : ViewModel() {

    // Dados de desempenho usando Compose State
    var desempenho by mutableStateOf<DashboardResponse?>(null)
        private set

    // Estado interno (privado) de carregamento
    private val _isLoading = MutableStateFlow(true)

    // Estado público (de leitura) de carregamento
    // ESSA LINHA ESTAVA FALTANDO! Agora a tela pode coletar este StateFlow.
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadPerformance(
        idAluno: Int,
        idMateria: Int? = null,
        idSemestre: Int? = null
    ) {
        viewModelScope.launch {
            // Usando .value para atualizar o MutableStateFlow privado
            _isLoading.value = true
            try {
                // Supondo que Conexao.desempenhoService está configurado
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
                // Usando .value para atualizar o MutableStateFlow privado
                _isLoading.value = false
            }
        }
    }
}