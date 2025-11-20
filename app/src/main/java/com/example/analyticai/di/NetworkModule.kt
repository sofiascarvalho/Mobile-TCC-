package com.example.analyticai.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

// IMPORTS CUSTOMIZADOS: Assumindo que seus serviços estão no pacote 'service'
import com.example.analyticai.service.LoginService
import com.example.analyticai.service.FiltrosApi
import com.example.analyticai.service.FiltrosApiImpl
import com.example.analyticai.service.DesempenhoService
import com.example.analyticai.service.RecoveryService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Módulo Hilt responsável por fornecer dependências relacionadas a Networking (Retrofit e OkHttp).
 *
 * O escopo [SingletonComponent::class] garante que todas as dependências fornecidas
 * por este módulo sejam Singletons, vivendo enquanto o processo da aplicação estiver ativo.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // URL base da sua API. Substitua pelo endereço real.
    // É recomendado carregar isso do BuildConfig ou de um arquivo de configuração.
    private const val BASE_URL = "http://192.168.0.103:8080/v1/analytica-ai/"
    private const val TIMEOUT_SECONDS = 30L

    /**
     * Fornece uma instância do OkHttpClient para gerenciamento de requisições HTTP.
     * Inclui um interceptor de log para debug.
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    /**
     * Fornece a instância do Retrofit configurada.
     */
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // --- SERVIÇOS CUSTOMIZADOS ---

    /**
     * Fornece a interface LoginService.
     */
    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    /**
     * Fornece a interface FiltrosApi.
     */
    @Provides
    @Singleton
    fun provideFiltrosApi(): FiltrosApi {
        return FiltrosApiImpl()
    }

    /**
     * Fornece a interface DesempenhoService.
     */
    @Provides
    @Singleton
    fun provideDesempenhoService(retrofit: Retrofit): DesempenhoService {
        return retrofit.create(DesempenhoService::class.java)
    }

    /**
     * Fornece o serviço de recuperação de senha.
     */
    @Provides
    @Singleton
    fun provideRecoveryService(retrofit: Retrofit): RecoveryService {
        return retrofit.create(RecoveryService::class.java)
    }

    // Você pode remover o AnalyticaiService de exemplo se ele for desnecessário.
    // @Provides
    // @Singleton
    // fun provideAnalyticaiService(retrofit: Retrofit): AnalyticaiService {
    //     return retrofit.create(AnalyticaiService::class.java)
    // }
}