package com.example.analyticai.model.Login

data class LoginResponse(
    val status: Boolean,
    val status_code: Int,
    val usuario: Usuario? // Pode ser nulo em caso de erro no login
)