package com.example.analyticai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.analyticai.model.Dashboard.DashboardState
import com.example.analyticai.model.Dashboard.DesempenhoResponse
import com.example.analyticai.model.Dashboard.FrequenciaResponse
import com.example.analyticai.model.Dashboard.AtividadeResponse
import com.example.analyticai.model.Dashboards.Materia
import com.example.analyticai.model.Dashboards.Semestre
import com.example.analyticai.model.Login.Usuario
import com.example.analyticai.model.Login.Turma
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class DashboardViewModel : ViewModel() {

    // --- ESTADO DO DASHBOARD ---
    private val _dashboardState = MutableStateFlow(DashboardState(isLoading = true))
    val dashboardState: StateFlow<DashboardState> = _dashboardState.asStateFlow()

    // --- ESTADOS DE FILTRO ---
    private val _selectedMateria = MutableStateFlow<Materia?>(null)
    val selectedMateria: StateFlow<Materia?> = _selectedMateria.asStateFlow()

    private val _selectedSemestre = MutableStateFlow<Semestre?>(null)
    val selectedSemestre: StateFlow<Semestre?> = _selectedSemestre.asStateFlow()

    init {
        // Inicializa o carregamento dos dados iniciais
        carregarDashboard()
    }

    /**
     * Define a matéria selecionada e dispara o carregamento do dashboard.
     */
    fun setSelectedMateria(materia: Materia?) {
        _selectedMateria.value = materia
        carregarDashboard() // Recarrega o dashboard ao mudar o filtro
    }

    /**
     * Define o semestre selecionado e dispara o carregamento do dashboard.
     */
    fun setSelectedSemestre(semestre: Semestre?) {
        _selectedSemestre.value = semestre
        carregarDashboard() // Recarrega o dashboard ao mudar o filtro
    }

    /**
     * Carrega dados mockados do dashboard baseado nos filtros selecionados.
     */
    fun carregarDashboard() {
        viewModelScope.launch {
            _dashboardState.value = _dashboardState.value.copy(isLoading = true, error = null)

            kotlinx.coroutines.delay(800) // Simula carregamento

            try {
                // Dados mockados baseados nos filtros
                val materiaSelecionada = _selectedMateria.value
                val semestreSelecionado = _selectedSemestre.value

                if (materiaSelecionada != null && semestreSelecionado != null) {
                    // Mock de dados baseado na seleção
                    val mockResponse = createMockDashboardData(materiaSelecionada, semestreSelecionado)
                    _dashboardState.value = _dashboardState.value.copy(
                        dashboard = mockResponse,
                        isLoading = false
                    )
                } else {
                    // Dados mockados padrão quando não há filtros
                    val mockResponse = createDefaultMockDashboardData()
                    _dashboardState.value = _dashboardState.value.copy(
                        dashboard = mockResponse,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _dashboardState.value = _dashboardState.value.copy(
                    isLoading = false,
                    error = "Erro ao carregar dados do dashboard: ${e.message}"
                )
            }
        }
    }

    private fun createMockDashboardData(materia: Materia, semestre: Semestre): DesempenhoResponse {
        return DesempenhoResponse(
            aluno = listOf(
                Usuario(
                    id_usuario = 1,
                    id_perfil = 1,
                    credencial = "joao@email.com",
                    nivel_usuario = "Aluno",
                    nome = "João Silva",
                    email = "joao@email.com",
                    telefone = "11999999999",
                    data_nascimento = "2000-01-01",
                    matricula = "2024001",
                    turma = null
                )
            ),
            id_semestre = semestre.id_semestre,
            semestre = semestre.name,
            frequencia = FrequenciaResponse(
                porcentagem_frequencia = "${Random.nextInt(70, 96)}%",
                presencas = Random.nextInt(15, 21),
                faltas = Random.nextInt(0, 6),
                total_aulas = 20
            ),
            materia = com.example.analyticai.model.MateriaResponse(
                materia_id = materia.id_materia,
                materia = materia.name
            ),
            atividades = listOf(
                AtividadeResponse("Prova 1", "Prova", Random.nextDouble(6.0, 10.1).toFloat(), "Avaliação mensal"),
                AtividadeResponse("Trabalho", "Trabalho", Random.nextDouble(7.0, 10.1).toFloat(), "Trabalho em grupo"),
                AtividadeResponse("Prova 2", "Prova", Random.nextDouble(5.0, 9.6).toFloat(), "Avaliação bimestral"),
                AtividadeResponse("Seminário", "Seminário", Random.nextDouble(8.0, 10.1).toFloat(), "Apresentação oral")
            ),
            media = String.format("%.1f", Random.nextDouble(6.5, 9.6))
        )
    }

    private fun createDefaultMockDashboardData(): DesempenhoResponse {
        return DesempenhoResponse(
            aluno = listOf(
                Usuario(
                    id_usuario = 1,
                    id_perfil = 1,
                    credencial = "joao@email.com",
                    nivel_usuario = "Aluno",
                    nome = "João Silva",
                    email = "joao@email.com",
                    telefone = "11999999999",
                    data_nascimento = "2000-01-01",
                    matricula = "2024001",
                    turma = null
                )
            ),
            id_semestre = 1,
            semestre = "2024.1",
            frequencia = FrequenciaResponse(
                porcentagem_frequencia = "85%",
                presencas = 17,
                faltas = 3,
                total_aulas = 20
            ),
            materia = com.example.analyticai.model.MateriaResponse(
                materia_id = 1,
                materia = "Matemática"
            ),
            atividades = listOf(
                AtividadeResponse("Prova 1", "Prova", 8.5f, "Avaliação mensal"),
                AtividadeResponse("Trabalho", "Trabalho", 9.0f, "Trabalho em grupo")
            ),
            media = "8.2"
        )
    }
}