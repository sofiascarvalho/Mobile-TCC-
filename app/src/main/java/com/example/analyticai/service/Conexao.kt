package com.example.analyticai.service

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// ⚠️ CORREÇÃO 1: Mudei para 'object' para acessar as propriedades estaticamente
object Conexao {
//    http://localhost:8080/v1/analytica-ai/aluno/11
    private const val BASE_URL = "http://10.0.2.2:8080/v1/analytica-ai/"

    val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val req = chain.request()
            Log.d("HTTP_DEBUG", "Request URL: ${req.url}")
            Log.d("HTTP_DEBUG", "Request method: ${req.method}")
            chain.proceed(req)
        }
        .build()

    // A instância do Retrofit é inicializada preguiçosamente (lazy)
    private val conexao: Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val desempenhoService: DesempenhoService by lazy {
        conexao.create(DesempenhoService::class.java)
    }

    // ⚠️ CORREÇÃO 2: Criamos uma propriedade 'val' (ou lazy val) que fornece o LoginService
    // O nome da propriedade deve ser minúsculo (convenção Kotlin)
    val loginService: LoginService by lazy {
        // CORREÇÃO 3: Usamos a referência à classe (::class.java), não o construtor ()
        conexao.create(LoginService::class.java)
    }
}