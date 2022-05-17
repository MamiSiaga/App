package com.mamisiaga.tools

import android.content.Context
import com.mamisiaga.api.APIConfig
import com.mamisiaga.repository.AnakRepository
import com.mamisiaga.repository.AutentikasiRepository
import com.mamisiaga.repository.IbuRepository
import com.mamisiaga.usecase.*

object Injection {
    private fun provideAutentikasiRepository(): AutentikasiRepository {
        val apiService = APIConfig.getAPIService()

        return AutentikasiRepository.getInstance(apiService)
    }

    fun provideAutentikasiUseCase(): AutentikasiUseCase {
        val repository = provideAutentikasiRepository()

        return AutentikasiInteractor(repository)
    }

    private fun provideIbuRepository(context: Context): IbuRepository {
        val apiService = APIConfig.getAPIService()

        return IbuRepository.getInstance(apiService)
    }

    fun provideIbuUseCase(context: Context): IbuUseCase {
        val repository = provideIbuRepository(context)

        return IbuInteractor(repository)
    }

    private fun provideAnakRepository(): AnakRepository {
        val apiService = APIConfig.getAPIService()

        return AnakRepository.getInstance(apiService)
    }

    fun provideAnakUseCase(): AnakUseCase {
        val repository = provideAnakRepository()

        return AnakInteractor(repository)
    }
}