package com.example.analyticai.model

data class DashboardResponse(
    val status: Boolean,
    val status_code: Int,
    val items: Int,
    val desempenho: List<Desempenho>
)