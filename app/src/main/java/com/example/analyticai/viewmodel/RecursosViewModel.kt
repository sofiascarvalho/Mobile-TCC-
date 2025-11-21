package com.example.analyticai.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.analyticai.data.SharedPreferencesManager
import com.example.analyticai.model.Recursos.Recurso
import com.example.analyticai.service.Conexao
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class RecursosViewModel(private val context: Context) : ViewModel() {

    var recursos by mutableStateOf<List<Recurso>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    private val sharedPrefs = SharedPreferencesManager(context)

    fun loadRecursos(materiaId: Int, semestreId: Int) {
        val usuario = sharedPrefs.getUsuario()
        val idPerfil = usuario?.id_perfil

        if (idPerfil == null) {
            errorMessage = "Não foi possível identificar o usuário."
            recursos = emptyList()
            return
        }

        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                val response = Conexao.recursosService.getRecursos(
                    idAluno = idPerfil,
                    materiaId = materiaId,
                    semestreId = semestreId
                )

                val itens = response.recursos

                if (itens.isEmpty()) {
                    recursos = emptyList()
                    errorMessage = "Não encontramos recursos para os filtros selecionados."
                } else {
                    recursos = itens
                }
            } catch (e: Exception) {
                Log.e("RecursosViewModel", "Erro ao carregar recursos", e)
                recursos = emptyList()
                errorMessage = "Não foi possível carregar os recursos. Tente novamente."
            } finally {
                isLoading = false
            }
        }
    }
}
