package com.example.analyticai.model.Dashboard

data class DashboardResponse(
    val status: Boolean,
    val status_code: Int,
    val items: Int,
    val desempenho: List<DesempenhoResponse>
)