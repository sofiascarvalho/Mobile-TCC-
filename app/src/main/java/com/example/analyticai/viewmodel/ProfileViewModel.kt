package com.example.analyticai.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.analyticai.data.SharedPreferencesManager
import com.example.analyticai.model.Profile.UpdateAlunoRequest
import com.example.analyticai.service.Conexao
import kotlinx.coroutines.launch

class ProfileViewModel(private val context: Context) : ViewModel() {

    private val sharedPrefs = SharedPreferencesManager(context)
    private var initialNome = ""
    private var initialEmail = ""
    private var initialTelefone = ""

    var nome by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var telefone by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var successMessage by mutableStateOf<String?>(null)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var hasChanges by mutableStateOf(false)
        private set

    init {
        loadUsuario()
    }

    private fun loadUsuario() {
        val usuario = sharedPrefs.getUsuario()
        if (usuario != null) {
            nome = usuario.nome
            email = usuario.email
            telefone = usuario.telefone
            initialNome = usuario.nome
            initialEmail = usuario.email
            initialTelefone = usuario.telefone
        } else {
            nome = ""
            email = ""
            telefone = ""
            initialNome = ""
            initialEmail = ""
            initialTelefone = ""
        }
        updateHasChanges()
    }

    fun updateNome(value: String) {
        nome = value
        clearMessages()
        updateHasChanges()
    }

    fun updateEmail(value: String) {
        email = value
        clearMessages()
        updateHasChanges()
    }

    fun updateTelefone(value: String) {
        telefone = value
        clearMessages()
        updateHasChanges()
    }

    fun clearMessages() {
        successMessage = null
        errorMessage = null
    }

    private fun updateHasChanges() {
        hasChanges =
            nome != initialNome || email != initialEmail || telefone != initialTelefone
    }

    fun salvarPerfil() {
        val usuario = sharedPrefs.getUsuario()
        val idPerfil = usuario?.id_perfil

        if (idPerfil == null) {
            errorMessage = "Não foi possível identificar o usuário."
            return
        }

        viewModelScope.launch {
            isLoading = true
            successMessage = null
            errorMessage = null
            try {
                val request = UpdateAlunoRequest(
                    nome = nome.trim(),
                    email = email.trim(),
                    telefone = telefone.trim()
                )

                val response = Conexao.alunoService.updateAluno(
                    perfilId = idPerfil,
                    request = request
                )

                if (response.status) {
                    successMessage = response.message
                    sharedPrefs.getUsuario()?.let {
                        val atualizado = it.copy(
                            nome = request.nome,
                            email = request.email,
                            telefone = request.telefone
                        )
                        sharedPrefs.saveUsuario(atualizado)
                    }
                    initialNome = request.nome
                    initialEmail = request.email
                    initialTelefone = request.telefone
                    updateHasChanges()
                } else {
                    errorMessage = response.message
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Erro ao atualizar perfil", e)
                errorMessage = "Não foi possível salvar as alterações. Tente novamente."
            } finally {
                isLoading = false
            }
        }
    }
}

