package com.example.analyticai.model

data class Aluno(
    val id_aluno: Int,
    val nome: String,
    val matricula: String,
    val data_nascimento: String,
    val telefone: String,
    val email: String,
    val turma: Turma
)