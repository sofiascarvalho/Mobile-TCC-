package com.example.analyticai.model

import android.credentials.Credential

data class Aluno(
    val id_aluno: Int,
    val id_usuario: Int,
    val credencial: String,
    val turma: Turma,
    val nome: String,
    val matricula: String,
    val telefone: String,
    val email: String,
    val data_nascimento: String,
)