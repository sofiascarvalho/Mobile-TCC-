package com.example.analyticai.model.Dashboard

data class InsightResponse(
    val status_code: Int,
    val message: String,
    val insight: Insight
)

data class Insight(
    val titulo: String,
    val conteudo: String,
    val data: String
)

