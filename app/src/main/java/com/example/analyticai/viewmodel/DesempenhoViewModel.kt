package com.example.analyticai.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.analyticai.model.Dashboard.DashboardResponse
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.analyticai.service.Conexao

class DesempenhoViewModel : ViewModel() {
    var desempenho by mutableStateOf<DashboardResponse?>(null)
        private set

    var isLoading by mutableStateOf(true)
        private set

    fun loadPerformance(alunoId: String) {
        viewModelScope.launch {
            isLoading = true
            try {
                val response: DashboardResponse = Conexao.desempenhoService.getDesempenho(alunoId)
                Log.d("DEBUG_API", "Response: $response")
                if (response.status && response.desempenho.isNotEmpty()) {
                    desempenho = response
                } else {
                    Log.d("DEBUG_API", "API retornou status falso ou lista vazia")
                    desempenho = null
                }
            } catch (e: Exception) {
                Log.e("DEBUG_API", "Erro ao carregar desempenho", e)
                desempenho = null
            } finally {
                isLoading = false
            }
        }
    }
}
