package com.example.analyticai.model.Ranking

import com.google.gson.annotations.SerializedName

data class RankingResponse(
    val status: Boolean,
    @SerializedName("status_code")
    val statusCode: Int,
    val items: Int,
    val ranking: List<RankingApiItem>
)

data class RankingApiItem(
    @SerializedName("Ranking")
    val ranking: Int?,
    @SerializedName("M\u00e9dia")
    val media: Double?,
    @SerializedName("nome")
    val nome: String
)

