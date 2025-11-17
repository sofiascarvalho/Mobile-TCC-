package com.example.analyticai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel

import com.example.analyticai.service.FiltrosApi // Necessário para a injeção
import com.example.analyticai.model.Dashboards.Materia
import com.example.analyticai.model.Dashboards.Semestre
import javax.inject.Inject

@HiltViewModel
class FiltrosViewModel @Inject constructor(
    // 1. ✅ PARÂMETRO INJETADO: Apenas as dependências do Hilt vão aqui.
    private val api: FiltrosApi // Agora o Hilt pode injetar o FiltrosApi
) : ViewModel() {

    // 2. ✅ VARIÁVEIS DE ESTADO: Declaradas dentro do corpo da classe, não no construtor.
    private val _materias = MutableStateFlow<List<Materia>>(emptyList())
    val materias: StateFlow<List<Materia>> = _materias

    private val _semestres = MutableStateFlow<List<Semestre>>(emptyList())
    val semestres: StateFlow<List<Semestre>> = _semestres

    init {
        carregarFiltros()
    }

    private fun carregarFiltros() {
        viewModelScope.launch {
            try {
                // Acessa o serviço injetado 'api'
                _materias.value = api.getMaterias().materias
                _semestres.value = api.getSemestres().semestres

            } catch (e: Exception) {
                e.printStackTrace()
                // Log do erro aqui
            }
        }
    }
}