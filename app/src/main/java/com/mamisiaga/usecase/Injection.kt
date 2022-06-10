package com.mamisiaga.usecase

import com.mamisiaga.api.APIConfig
import com.mamisiaga.repository.*

object Injection {
    private fun provideUserRepository() : UserRepository {
        val apiService = APIConfig.getAPIService()
        return UserRepository.getInstance(apiService)
    }

    fun provideUserUseCase() : UserUseCase {
        val repo = provideUserRepository()
        return UserInteractor(repo)
    }

    private fun provideAutentikasiRepository(): AutentikasiRepository {
        val apiService = APIConfig.getAPIService()

        return AutentikasiRepository.getInstance(apiService)
    }

    fun provideAutentikasiUseCase(): AutentikasiUseCase {
        val repository = provideAutentikasiRepository()

        return AutentikasiInteractor(repository)
    }

    private fun provideIbuRepository(token: String): IbuRepository {
        val apiService = APIConfig.getAPIService(token)

        return IbuRepository.getInstance(apiService)
    }

    fun provideIbuUseCase(token: String): IbuUseCase {
        val repository = provideIbuRepository(token)

        return IbuInteractor(repository)
    }

    private fun provideKehamilanRepository(token: String): KehamilanRepository {
        val apiService = APIConfig.getAPIService(token)

        return KehamilanRepository.getInstance(apiService)
    }

    fun provideKehamilanUseCase(token: String): KehamilanUseCase {
        val repository = provideKehamilanRepository(token)

        return KehamilanInteractor(repository)
    }

    private fun provideAnakRepository(token: String): AnakRepository {
        val apiService = APIConfig.getAPIService(token)

        return AnakRepository.getInstance(apiService)
    }

    fun provideAnakUseCase(token: String): AnakUseCase {
        val repository = provideAnakRepository(token)

        return AnakInteractor(repository)
    }

    private fun providePertumbuhanRepository(token: String): PertumbuhanRepository {
        val apiService = APIConfig.getAPIService(token)

        return PertumbuhanRepository.getInstance(apiService)
    }

    fun providePertumbuhanUseCase(token: String): PertumbuhanUseCase {
        val repository = providePertumbuhanRepository(token)

        return PertumbuhanInteractor(repository)
    }

    private fun provideImunisasiRepository(token: String): ImunisasiRepository {
        val apiService = APIConfig.getAPIService(token)

        return ImunisasiRepository.getInstance(apiService)
    }

    fun provideImunisasiUseCase(token: String): ImunisasiUseCase {
        val repository = provideImunisasiRepository(token)

        return ImunisasiInteractor(repository)
    }
}