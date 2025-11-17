package com.example.analyticai.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.analyticai.model.Dashboard.DashboardResponse
import com.example.analyticai.service.Conexao
import com.example.analyticai.data.SharedPreferencesManager
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.analyticai.model.Login.Usuario

class RankingViewModel(private val context: Context) : ViewModel() {

    var alunoMedia by mutableStateOf<Double?>(null)
        private set

    var rankingData by mutableStateOf<DashboardResponse?>(null)
        private set

    var isLoading by mutableStateOf(false)

    fun loadRankingAndPerformance() {

        // Carrega o usuário salvo no SharedPreferences
        val sharedPrefs = SharedPreferencesManager(context)
        val usuario = sharedPrefs.getUsuario()

        if (usuario == null) {
            Log.e("RankingViewModel", "Usuário não encontrado no SharedPreferences!")
            return
        }

        // O ID correto é usuario.id (vem do seu SharedPreferencesManager)
        val idAluno = usuario.id_usuario

        if (idAluno == null) {
            Log.e("RankingViewModel", "ID do usuário é nulo!")
            return
        }

        viewModelScope.launch {
            isLoading = true
            try {

                // ⚠ VERIFICAR se o endpoint espera INT ou STRING
                val response = Conexao.desempenhoService.getDesempenho(idAluno)

                Log.d("DEBUG_API_RANKING", "Resposta carregada: $response")

                rankingData = response

            } catch (e: Exception) {
                Log.e("DEBUG_API_RANKING", "Erro ao carregar dados", e)
                rankingData = null
            } finally {
                isLoading = false
            }
        }
    }
}
