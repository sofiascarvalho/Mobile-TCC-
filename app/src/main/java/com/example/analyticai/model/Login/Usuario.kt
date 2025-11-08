package com.example.analyticai.model.Login

data class Usuario(
    val id_usuario: Int,
    val id_perfil: Int,
    val credencial: String,
    val nivel_usuario: String, // Usado para saber quem é o usuário
    val nome: String,
    val email: String,
    val telefone: String,

    // Campos Opcionais
    // Presente apenas em Aluno e Professor
    val data_nascimento: String?, // Adicionado '?' (nullable)

    // Presente apenas em Aluno
    val matricula: String?,       // Adicionado '?'
    val turma: Turma?             // Adicionado '?'
)