package com.mamisiaga.usecase

import android.content.Context
import com.mamisiaga.api.APIConfig
import com.mamisiaga.repository.*
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

    private fun provideKehamilanRepository(): KehamilanRepository {
        val apiService = APIConfig.getAPIService()

        return KehamilanRepository.getInstance(apiService)
    }

    fun provideKehamilanUseCase(): KehamilanUseCase {
        val repository = provideKehamilanRepository()

        return KehamilanInteractor(repository)
    }

    private fun provideAnakRepository(): AnakRepository {
        val apiService = APIConfig.getAPIService()

        return AnakRepository.getInstance(apiService)
    }

    fun provideAnakUseCase(): AnakUseCase {
        val repository = provideAnakRepository()

        return AnakInteractor(repository)
    }

    private fun providePertumbuhanRepository(): PertumbuhanRepository {
        val apiService = APIConfig.getAPIService()

        return PertumbuhanRepository.getInstance(apiService)
    }

    fun providePertumbuhanUseCase(): PertumbuhanUseCase {
        val repository = providePertumbuhanRepository()

        return PertumbuhanInteractor(repository)
    }

    private fun provideImunisasiRepository(): ImunisasiRepository {
        val apiService = APIConfig.getAPIService()

        return ImunisasiRepository.getInstance(apiService)
    }

    fun provideImunisasiUseCase(): ImunisasiUseCase {
        val repository = provideImunisasiRepository()

        return ImunisasiInteractor(repository)
    }
}