//package com.example.analyticai.viewmodel
//
//import android.content.Context
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.analyticai.model.Dashboard.DashboardResponse
//import com.example.analyticai.service.Conexao
//import com.example.analyticai.data.SharedPreferencesManager // Adicione o import
//import kotlinx.coroutines.launch
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.setValue

//class RankingViewModel(private val context: Context) : ViewModel() {
//
//    // Estado que irá armazenar a nota do aluno logado (para destaque)
//    var alunoMedia by mutableStateOf<Double?>(null)
//        private set
//
//    // Estado que irá armazenar a lista completa de ranking (DashboardResponse, se ela retornar tudo)
//    // O tipo deve ser a resposta completa da API (DashboardResponse, adaptada para Ranking)
//    var rankingData by mutableStateOf<DashboardResponse?>(null)
//        private set
//
//    var isLoading by mutableStateOf(false)
//
//    // Remova a função loadPerformance antiga. Esta é a nova.
//    fun loadRankingAndPerformance() {
//        // Obtenha o ID do aluno e o Token (se necessário) do SharedPreferences
//        val sharedPrefs = SharedPreferencesManager(context)
//        val idAluno = sharedPrefs.getUsuario()?.id // Assumindo que o ID do aluno está no objeto Usuario
//
//        if (idAluno == null) {
//            Log.e("RankingViewModel", "ID do aluno não encontrado. Usuário não logado.")
//            return
//        }
//
//        viewModelScope.launch {
//            isLoading = true
//            try {
//                // Chama o endpoint de Desempenho
//                val response: DashboardResponse = Conexao.desempenhoService.getDesempenho(idAluno)
//
//                Log.d("DEBUG_API_RANKING", "Desempenho carregado: $response")
//
//                // ⚠️ Se o DashboardResponse contiver a nota do aluno:
//                // alunoMedia.value = response.getMediaNumerica() // (Se você criou a função helper)
//
//                // ⚠️ Se a API retornar todos os dados do Ranking (o que é ideal):
//                rankingData = response
//
//            } catch (e: Exception) {
//                Log.e("DEBUG_API_RANKING", "Erro ao carregar Ranking", e)
//                rankingData = null
//            } finally {
//                isLoading = false
//            }
//        }
//    }
//}