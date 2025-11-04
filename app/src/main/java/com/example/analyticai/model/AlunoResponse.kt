package com.example.analyticai.model

data class AlunoResponse(
    val status: Boolean,
    val status_code: Int,
    val items: Int,
    val alunos: List<Aluno>
)
