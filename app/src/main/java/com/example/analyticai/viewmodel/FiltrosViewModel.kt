package com.example.analyticai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import com.example.analyticai.service.FiltrosApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.analyticai.model.Dashboards.Materia
import com.example.analyticai.model.Dashboards.Semestre

@HiltViewModel
class FiltrosViewModel @Inject constructor(
    private val api: FiltrosApi
) : ViewModel() {

    // 1. Estados dos Filtros
    private val _materias = MutableStateFlow<List<Materia>>(emptyList())
    val materias: StateFlow<List<Materia>> = _materias.asStateFlow()

    private val _semestres = MutableStateFlow<List<Semestre>>(emptyList())
    val semestres: StateFlow<List<Semestre>> = _semestres.asStateFlow()

    // 2. Estado de Carregamento (CORRIGIDO):
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        carregarFiltros()
    }

    fun carregarFiltros() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _materias.value = api.getMaterias().materias
                _semestres.value = api.getSemestres().semestres
            } catch (e: Exception) {
                e.printStackTrace()
                // Em caso de erro, você pode querer manter o isLoading=false,
                // mas também mostrar uma mensagem de erro na tela (não implementado aqui).
            } finally {
                _isLoading.value = false
            }
        }
    }
}