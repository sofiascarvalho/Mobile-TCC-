package com.example.analyticai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.analyticai.model.response.RecursoResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecursosViewModel : ViewModel() {
    private val _recursos =
        MutableStateFlow<List<RecursoResponse>>(emptyList())

    val recursos: StateFlow<List<RecursoResponse>> = _recursos

    init {
        carregarRecursosIniciais()
    }

    private fun carregarRecursosIniciais() {
        viewModelScope.launch {
            _recursos.value - listOf(
                RecursoResponse(
                    titulo = "Material Complementar: Filosofia",
                    descricao = "Material para estudo complementar da aula de segunda-feira",
                    disciplina = "Filosofia",
                    periodo = "1º Semestre",
                    arquivo = "materia-filosofia.pdf"
                ),
                RecursoResponse(
                    titulo = "Material Complementar: Filosofia",
                    descricao = "Material para estudo complementar da aula de segunda-feira",
                    disciplina = "Filosofia",
                    periodo = "1º Semestre",
                    arquivo = "materia-filosofia.pdf"
                ),
                RecursoResponse(
                    titulo = "Material Complementar: Filosofia",
                    descricao = "Material para estudo complementar da aula de segunda-feira",
                    disciplina = "Filosofia",
                    periodo = "1º Semestre",
                    arquivo = "materia-filosofia.pdf"
                )
            )
        }
    }
}