package com.example.analyticai.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// ⚠️ CORREÇÃO 1: Mudei para 'object' para acessar as propriedades estaticamente
object Conexao {

    // A instância do Retrofit é inicializada preguiçosamente (lazy)
    private val conexao: Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // ⚠️ CORREÇÃO 2: Criamos uma propriedade 'val' (ou lazy val) que fornece o LoginService
    // O nome da propriedade deve ser minúsculo (convenção Kotlin)
    val loginService: LoginService by lazy {
        // CORREÇÃO 3: Usamos a referência à classe (::class.java), não o construtor ()
        conexao.create(LoginService::class.java)
    }
}