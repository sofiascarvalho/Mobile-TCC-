package com.example.analyticai.model

data class AlunoResponse(
    val id_usuario: Int,
    val id_perfil: Int,
    val credencial: String,
    val nivel_usuario: String,
    val nome: String,
    val email: String,
    val telefone: String,
    val data_nascimento: String,
    val matricula: String,
    val turma: TurmaResponse
)