package com.example.analyticai.viewmodel

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

    fun loadPerformance() {
        viewModelScope.launch {
            try {
                val response = Conexao.desempenhoService.getDesempenho()
                if (response.status && response.desempenho.isNotEmpty()) {
                    desempenho = response
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }
}
