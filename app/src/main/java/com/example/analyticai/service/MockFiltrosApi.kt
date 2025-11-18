package com.example.analyticai.service

import com.example.analyticai.model.Dashboards.MateriaResponse
import com.example.analyticai.model.Dashboards.Materia
import com.example.analyticai.model.Dashboards.SemestreResponse
import com.example.analyticai.model.Dashboards.Semestre

class MockFiltrosApi : FiltrosApi {
    
    override suspend fun getMaterias(): MateriaResponse {
        // Simula delay de rede
        kotlinx.coroutines.delay(500)
        
        return MateriaResponse(
            status = true,
            status_code = 200,
            items = 5,
            materias = listOf(
                Materia(1, "Matemática", "#FF6B6B"),
                Materia(2, "Português", "#4ECDC4"),
                Materia(3, "História", "#45B7D1"),
                Materia(4, "Geografia", "#96CEB4"),
                Materia(5, "Ciências", "#FFEAA7")
            )
        )
    }
    
    override suspend fun getSemestres(): SemestreResponse {
        // Simula delay de rede
        kotlinx.coroutines.delay(300)
        
        return SemestreResponse(
            status = true,
            status_code = 200,
            items = 4,
            semestres = listOf(
                Semestre(1, "2024.1"),
                Semestre(2, "2024.2"),
                Semestre(3, "2025.1"),
                Semestre(4, "2025.2")
            )
        )
    }
}
