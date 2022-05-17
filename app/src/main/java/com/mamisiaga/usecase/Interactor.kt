package com.mamisiaga.usecase

import androidx.lifecycle.LiveData
import com.mamisiaga.`class`.IbuDaftar
import com.mamisiaga.api.GetAnakResponse
import com.mamisiaga.api.GetIbuResponse
import com.mamisiaga.repository.AnakRepository
import com.mamisiaga.repository.AutentikasiRepository
import com.mamisiaga.repository.IbuRepository
import com.mamisiaga.tools.ResultResponse
import kotlinx.coroutines.flow.Flow

/*
class IbuInteractor(private val ibuRepository: IbuRepository) : IbuUseCase {
    override fun getAllIbu() = ibuRepository.getIbu()
}
 */

class AutentikasiInteractor(private val autentikasiRepository: AutentikasiRepository) :
    AutentikasiUseCase {
    override fun daftar(ibuDaftar: IbuDaftar) =
        autentikasiRepository.daftar(ibuDaftar)

    override fun masuk(email: String, kataSandi: String) =
        autentikasiRepository.masuk(email, kataSandi)
}

class IbuInteractor(private val ibuRepository: IbuRepository) :
    IbuUseCase {

    override fun getIbu(): Flow<ResultResponse<GetIbuResponse>> {
        TODO("Not yet implemented")
    }
}

class AnakInteractor(private val anakRepository: AnakRepository) :
    AnakUseCase {

    override fun getAnak(): Flow<ResultResponse<GetAnakResponse>> {
        TODO("Not yet implemented")
    }
}