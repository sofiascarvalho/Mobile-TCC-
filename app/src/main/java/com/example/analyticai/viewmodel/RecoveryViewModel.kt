package com.example.analyticai.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.analyticai.data.repository.RecoveryRepository
import com.example.analyticai.model.RecoveryResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecoveryViewModel @Inject constructor(
    private val recoveryRepository: RecoveryRepository
) : ViewModel() {

    private val _recoveryState = MutableStateFlow<RecoveryState>(RecoveryState.Idle)
    val recoveryState: StateFlow<RecoveryState> = _recoveryState

    fun validarCredencial(credencial: String): String? {
        return when {
            credencial.isBlank() -> "A matrícula é obrigatória."
            credencial.length < 5 -> "A matrícula deve ter pelo menos 5 dígitos."
            else -> null
        }
    }

    fun recuperarSenha(credencial: String) {
        viewModelScope.launch {
            _recoveryState.value = RecoveryState.Loading
            try {
                val response = recoveryRepository.recoverPassword(credencial)
                if (response.status) {
                    _recoveryState.value = RecoveryState.Success(response.message)
                } else {
                    _recoveryState.value = RecoveryState.Error(response.message ?: "Erro desconhecido")
                }
            } catch (e: Exception) {
                _recoveryState.value = RecoveryState.Error("Falha na conexão. Tente novamente.")
            }
        }
    }

    fun resetState() {
        _recoveryState.value = RecoveryState.Idle
    }
}

sealed class RecoveryState {
    object Idle : RecoveryState()
    object Loading : RecoveryState()
    data class Success(val message: String) : RecoveryState()
    data class Error(val message: String) : RecoveryState()
}
