package com.example.analyticai.model.Recursos

import com.google.gson.annotations.SerializedName

data class RecursoResponse(
    val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    val items: Int,
    val recursos: List<Recurso>
)
