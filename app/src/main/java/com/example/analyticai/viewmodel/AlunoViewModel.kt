package com.example.analyticai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.analyticai.model.Aluno
import com.example.analyticai.data.ApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlunoViewModel(private val repository: ApiRepository) : ViewModel() {

    // _alunoLogado é um MutableStateFlow observável e mutável
    private val _alunoLogado = MutableStateFlow<Aluno?>(null)

    // Versão pública e imutável, evitando alterações diretas
    val alunoLogado: StateFlow<Aluno?> = _alunoLogado

    fun carregarAlunoPorCredencial(credencial: String) {
        viewModelScope.launch {
            val response = repository.getAlunos()
            if (response.isSuccessful) {
                val aluno = response.body()?.alunos?.find { it.credencial == credencial }
                _alunoLogado.value = aluno
            } else {
                _alunoLogado.value = null
            }
        }
    }
}
