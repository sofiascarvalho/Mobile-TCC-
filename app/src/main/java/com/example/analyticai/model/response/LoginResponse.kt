package com.example.analyticai.model.response

data class LoginResponse(
    val status: Boolean,
    val status_code: Int,
    val usuario: AlunoResponse
)