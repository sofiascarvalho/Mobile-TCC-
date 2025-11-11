package com.example.analyticai.service
package com.exemple.analyticai.model.Dashboard.DashboardResponse
import retrofit2.http.GET

interface DesempenhoService {
    @GET("desempenho/aluno")
    suspend fun getDesempenho(): DashboardResponse
}