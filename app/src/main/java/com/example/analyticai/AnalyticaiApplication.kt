package com.example.analyticai

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Classe principal da aplicação que estende Application e é anotada com @HiltAndroidApp.
 * * Esta anotação inicia a geração de código do Hilt, incluindo o container
 * de dependências no nível da aplicação. Este container pode fornecer
 * dependências para outros módulos e componentes injetados.
 * * NOTA: Esta classe deve ser registrada no AndroidManifest.xml.
 */
@HiltAndroidApp
class AnalyticaiApplication : Application() {
    // Não é necessário adicionar código aqui, a menos que você tenha lógica de inicialização
    // de bibliotecas de terceiros que não dependa de injeção.
}