package com.example.analyticai.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.analyticai.model.Desempenho
import com.example.analyticai.service.DashboardService
import com.example.analyticai.service.RetrofitFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(private val service: DashboardService) : ViewModel() {

    private val _desempenho = MutableStateFlow<List<Desempenho>>(emptyList())
    val desempenho: StateFlow<List<Desempenho>> = _desempenho

    private val _materias = MutableStateFlow<List<String>>(emptyList())
    val materias: StateFlow<List<String>> = _materias

    private val _selectedMateria = MutableStateFlow("Todas")
    val selectedMateria: StateFlow<String> = _selectedMateria

    fun buscarDesempenho(idAluno: Int) {
        viewModelScope.launch {
            try {
                val response = service.getDesempenhoAluno(idAluno)
                if (response.status) {
                    _desempenho.value = response.desempenho
                    _materias.value = listOf("Todas") + response.desempenho.map { it.materia }.distinct()
                }
            } catch (e: Exception) {
                Log.e("DashboardVM", "Erro ao buscar desempenho", e)
            }
        }
    }

    fun setSelectedMateria(materia: String) {
        _selectedMateria.value = materia
    }
}



