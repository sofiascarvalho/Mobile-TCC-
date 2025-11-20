package com.example.analyticai.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.analyticai.data.SharedPreferencesManager
import com.example.analyticai.model.Ranking.RankingItem
import com.example.analyticai.service.Conexao
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class RankingViewModel(private val context: Context) : ViewModel() {

    var rankingItems by mutableStateOf<List<RankingItem>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    private val sharedPrefs = SharedPreferencesManager(context)

    fun loadRanking(materiaId: Int, semestreId: Int) {
        val usuario = sharedPrefs.getUsuario()

        val idPerfil = usuario?.id_perfil
        if (idPerfil == null) {
            errorMessage = "Não foi possível identificar o usuário."
            rankingItems = emptyList()
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val response = Conexao.rankingService.getRanking(
                    perfilId = idPerfil,
                    materiaId = materiaId,
                    semestreId = semestreId
                )

                val itens = response.ranking

                if (itens.isEmpty()) {
                    rankingItems = emptyList()
                    errorMessage = "Não encontramos dados para os filtros selecionados."
                } else {
                    rankingItems = itens.mapIndexed { index, item ->
                        RankingItem(
                            posicao = index + 1,
                            media = item.media ?: 0.0,
                            nome = item.nome
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("RankingViewModel", "Erro ao carregar ranking", e)
                rankingItems = emptyList()
                errorMessage = "Não foi possível carregar o ranking. Tente novamente."
            } finally {
                isLoading = false
            }
        }
    }
}
