package com.example.analyticai.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import com.example.analyticai.data.SharedPreferencesManager
import com.example.analyticai.data.repository.RecoveryRepository
import com.example.analyticai.service.RecoveryService

/**
 * Módulo Hilt responsável por fornecer dependências relacionadas à persistência de dados.
 *
 * Ele configura e fornece uma instância Singleton do DataStore de Preferências.
 */
@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    // Nome do arquivo onde o DataStore irá salvar as preferências.
    private const val USER_PREFERENCES_NAME = "user_preferences"

    /**
     * Fornece a instância Singleton do DataStore<Preferences>.
     *
     * O Hilt injeta o Context da aplicação (@ApplicationContext) automaticamente.
     */
    @Provides
    @Singleton
    fun provideUserPreferencesDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = null,
            migrations = emptyList(),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        ) {
            context.preferencesDataStoreFile(USER_PREFERENCES_NAME)
        }
    }

    @Provides
    @Singleton
    fun provideSharedPreferencesManager(
        @ApplicationContext context: Context
    ): SharedPreferencesManager {
        return SharedPreferencesManager(context)
    }

    @Provides
    @Singleton
    fun provideRecoveryRepository(
        recoveryService: RecoveryService
    ): RecoveryRepository {
        return RecoveryRepository(recoveryService)
    }


    // Você pode adicionar aqui outras injeções relacionadas a dados, como Room Database.
}