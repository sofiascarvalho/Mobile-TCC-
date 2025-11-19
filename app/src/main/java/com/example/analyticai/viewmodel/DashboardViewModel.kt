package com.example.analyticai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.analyticai.data.SharedPreferencesManager
import com.example.analyticai.model.Dashboard.DashboardResponse
import com.example.analyticai.model.Dashboard.DashboardState
import com.example.analyticai.model.Dashboard.DesempenhoResponse
import com.example.analyticai.model.Dashboard.FrequenciaResponse
import com.example.analyticai.model.Dashboard.Insight
import com.example.analyticai.model.Dashboard.RelatorioCardState
import com.example.analyticai.model.Dashboard.RelatorioTipo
import com.example.analyticai.model.Dashboards.Materia
import com.example.analyticai.model.Dashboards.Semestre
import com.example.analyticai.model.MateriaResponse
import com.example.analyticai.service.DesempenhoService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val desempenhoService: DesempenhoService,
    private val sharedPreferencesManager: SharedPreferencesManager
) : ViewModel() {

    private val _dashboardState = MutableStateFlow(
        DashboardState(
            dashboard = createPlaceholderDashboardData(),
            isLoading = false,
            error = null,
            isPlaceholder = true
        )
    )
    val dashboardState: StateFlow<DashboardState> = _dashboardState.asStateFlow()

    private val _relatoriosState = MutableStateFlow(defaultRelatorioStates())
    val relatoriosState: StateFlow<List<RelatorioCardState>> = _relatoriosState.asStateFlow()

    private val _selectedMateria = MutableStateFlow<Materia?>(null)
    val selectedMateria: StateFlow<Materia?> = _selectedMateria.asStateFlow()

    private val _selectedSemestre = MutableStateFlow<Semestre?>(null)
    val selectedSemestre: StateFlow<Semestre?> = _selectedSemestre.asStateFlow()

    private val _insightState = MutableStateFlow<InsightState>(
        InsightState(
            insight = null,
            isLoading = false,
            error = null
        )
    )
    val insightState: StateFlow<InsightState> = _insightState.asStateFlow()

    fun setSelectedMateria(materia: Materia?) {
        _selectedMateria.value = materia
        handleFilterUpdate()
    }

    fun setSelectedSemestre(semestre: Semestre?) {
        _selectedSemestre.value = semestre
        handleFilterUpdate()
    }

    fun carregarDashboard() {
        handleFilterUpdate()
    }

    private fun handleFilterUpdate() {
        val materia = _selectedMateria.value
        val semestre = _selectedSemestre.value
        if (materia != null && semestre != null) {
            // Resetar insights quando mudar os filtros
            resetInsightState()
            fetchDashboard(materia, semestre)
        } else {
            setPlaceholderState(
                message = null,
                relatoriosMessage = RELATORIO_FILTROS_NAO_SELECIONADOS,
                showError = false
            )
            resetInsightState()
        }
    }

    private fun fetchDashboard(materia: Materia, semestre: Semestre) {
        viewModelScope.launch {
            _dashboardState.value = _dashboardState.value.copy(isLoading = true, error = null)
            val usuario = sharedPreferencesManager.getUsuario()
            if (usuario == null) {
                setPlaceholderState(
                    message = "Usuário não encontrado.",
                    relatoriosMessage = RELATORIO_INDISPONIVEL
                )
                return@launch
            }

            try {
                val response = desempenhoService.getDesempenho(
                    idAluno = usuario.id_perfil,
                    idMateria = materia.id_materia,
                    idSemestre = semestre.id_semestre
                )

                val desempenho = response.desempenho.firstOrNull()
                if (desempenho != null) {
                    _dashboardState.value = DashboardState(
                        dashboard = desempenho,
                        isLoading = false,
                        error = null,
                        isPlaceholder = false
                    )
                    gerarRelatorios(response, materia, semestre)
                    fetchInsights(response, materia, semestre)
                } else {
                    setPlaceholderState(
                        message = "Sem dados para os filtros selecionados.",
                        relatoriosMessage = RELATORIO_INDISPONIVEL
                    )
                    resetInsightState()
                }
            } catch (e: Exception) {
                setPlaceholderState(
                    message = "Não foi possível carregar os dados.",
                    relatoriosMessage = RELATORIO_INDISPONIVEL
                )
                resetInsightState()
            }
        }
    }

    private fun fetchInsights(
        response: DashboardResponse,
        materia: Materia,
        semestre: Semestre
    ) {
        viewModelScope.launch {
            // Garantir que o estado de loading seja definido
            _insightState.value = InsightState(
                insight = null,
                isLoading = true,
                error = null
            )

            try {
                val insightResponse = desempenhoService.getInsights(
                    idMateria = materia.id_materia,
                    idSemestre = semestre.id_semestre,
                    body = response
                )

                // Verificar se a resposta tem status_code 200 e insight não nulo
                if (insightResponse.status_code == 200 && insightResponse.insight != null) {
                    _insightState.value = InsightState(
                        insight = insightResponse.insight,
                        isLoading = false,
                        error = null
                    )
                } else {
                    // Se não houver insight válido, voltar ao estado inicial
                    _insightState.value = InsightState(
                        insight = null,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                // Em caso de erro, voltar ao estado inicial
                // Log do erro pode ser adicionado aqui para debug
                _insightState.value = InsightState(
                    insight = null,
                    isLoading = false,
                    error = null
                )
            }
        }
    }

    private fun resetInsightState() {
        _insightState.value = InsightState(
            insight = null,
            isLoading = false,
            error = null
        )
    }

    private fun setPlaceholderState(
        message: String? = null,
        relatoriosMessage: String = RELATORIO_INDISPONIVEL,
        showError: Boolean = true
    ) {
        _dashboardState.value = DashboardState(
            dashboard = createPlaceholderDashboardData(),
            isLoading = false,
            error = if (showError) message else null,
            isPlaceholder = true
        )
        updateAllRelatoriosWithMessage(relatoriosMessage)
    }

    private fun gerarRelatorios(
        response: DashboardResponse,
        materia: Materia,
        semestre: Semestre
    ) {
        viewModelScope.launch {
            setRelatoriosLoading(message = "Gerando relatórios para ${materia.materia}...")

            val materiaId = materia.id_materia
            val semestreId = semestre.id_semestre

            val requests = listOf(
                RelatorioTipo.COMPLETO to async {
                    desempenhoService.gerarRelatorioCompleto(materiaId, semestreId, response)
                },
                RelatorioTipo.DESEMPENHO to async {
                    desempenhoService.gerarRelatorioDesempenho(materiaId, semestreId, response)
                },
                RelatorioTipo.FREQUENCIA to async {
                    desempenhoService.gerarRelatorioFrequencia(materiaId, semestreId, response)
                }
            )

            requests.forEach { (tipo, deferred) ->
                try {
                    val resultado = deferred.await()
                    val info = resultado.relatorio
                    val link = formatLink(info.link)
                    if (link != null) {
                        updateRelatorioState(tipo) {
                            it.copy(
                                isLoading = false,
                                isEnabled = true,
                                link = link,
                                lastUpdate = info.data,
                                hasError = false,
                                statusMessage = "Disponível para download"
                            )
                        }
                    } else {
                        markRelatorioErro(tipo)
                    }
                } catch (e: Exception) {
                    markRelatorioErro(tipo)
                }
            }
        }
    }

    private fun setRelatoriosLoading(message: String) {
        _relatoriosState.value = _relatoriosState.value.map {
            it.copy(
                isLoading = true,
                isEnabled = false,
                link = null,
                lastUpdate = null,
                hasError = false,
                statusMessage = message
            )
        }
    }

    private fun updateAllRelatoriosWithMessage(message: String) {
        _relatoriosState.value = defaultRelatorioStates(message)
    }

    private fun updateRelatorioState(
        tipo: RelatorioTipo,
        transform: (RelatorioCardState) -> RelatorioCardState
    ) {
        _relatoriosState.value = _relatoriosState.value.map {
            if (it.tipo == tipo) transform(it) else it
        }
    }

    private fun markRelatorioErro(tipo: RelatorioTipo) {
        updateRelatorioState(tipo) {
            it.copy(
                isLoading = false,
                isEnabled = false,
                link = null,
                lastUpdate = null,
                hasError = true,
                statusMessage = RELATORIO_INDISPONIVEL
            )
        }
    }

    private fun formatLink(link: String?): String? {
        if (link.isNullOrBlank()) return null
        return if (link.startsWith("http")) link else "$DOWNLOAD_BASE_URL$link"
    }

    companion object {
        private const val DOWNLOAD_BASE_URL = "http://192.168.0.103:8080"
        private const val RELATORIO_FILTROS_NAO_SELECIONADOS =
            "Selecione os filtros para gerar os relatórios."
        private const val RELATORIO_INDISPONIVEL =
            "Não foi possível encontrar relatórios para os filtros selecionados."

        private fun createPlaceholderDashboardData(): DesempenhoResponse {
            return DesempenhoResponse(
                aluno = null,
                frequencia = FrequenciaResponse(
                    porcentagem_frequencia = "0%",
                    presencas = 0,
                    faltas = 0,
                    total_aulas = 0
                ),
                materia = MateriaResponse(
                    materia_id = 0,
                    materia = "Selecione uma matéria"
                ),
                atividades = emptyList(),
                media = "0.0"
            )
        }

        private fun defaultRelatorioStates(
            message: String = RELATORIO_FILTROS_NAO_SELECIONADOS
        ): List<RelatorioCardState> {
            return RelatorioTipo.values().map { tipo ->
                RelatorioCardState(
                    tipo = tipo,
                    statusMessage = message,
                    isEnabled = false,
                    isLoading = false,
                    link = null,
                    lastUpdate = null,
                    hasError = false
                )
            }
        }
    }
}

data class InsightState(
    val insight: Insight?,
    val isLoading: Boolean,
    val error: String?
)