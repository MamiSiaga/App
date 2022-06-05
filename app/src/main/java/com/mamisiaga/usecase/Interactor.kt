package com.mamisiaga.usecase

import com.mamisiaga.dataClass.Anak
import com.mamisiaga.dataClass.IbuDaftar
import com.mamisiaga.api.*
import com.mamisiaga.repository.*
import com.mamisiaga.tools.ResultResponse
import kotlinx.coroutines.flow.Flow

/*
dataClass IbuInteractor(private val ibuRepository: IbuRepository) : IbuUseCase {
    override fun getAllIbu() = ibuRepository.getIbuById()
}
 */

class UserInteractor(private val repo: UserRepository) : UserUseCase {

    override fun getUser(token: String): Flow<ResultResponse<GetUserResponse>> =
        repo.getUser(token)

}

class AutentikasiInteractor(private val autentikasiRepository: AutentikasiRepository) :
    AutentikasiUseCase {
    override fun daftar(ibuDaftar: IbuDaftar) =
        autentikasiRepository.daftar(ibuDaftar)

    override fun masuk(email: String, kataSandi: String) =
        autentikasiRepository.masuk(email, kataSandi)
}

class IbuInteractor(private val ibuRepository: IbuRepository) :
    IbuUseCase {

    override fun getIbuById(id: Int): Flow<ResultResponse<GetIbuResponse>> = ibuRepository.getIbuByIdResponse(id)

    override fun getIbuByToken(): Flow<ResultResponse<GetIbuResponse>> = ibuRepository.getIbuByTokenResponse()
}

class KehamilanInteractor(private val kehamilanRepository: KehamilanRepository) : KehamilanUseCase {
    override fun getKontrolKehamilan(id: Int): Flow<ResultResponse<GetKontrolKehamilanResponse>> {
        TODO("Not yet implemented")
    }

    /*
    override fun getKontrolKehamilanNotDone(): Flow<ResultResponse<GetKontrolKehamilanNotDoneResponse>>

    override fun getKontrolKehamilanDone(): Flow<ResultResponse<GetKontrolKehamilanDoneResponse>>

    override fun addKontrolKehamilan(): Flow<ResultResponse<AddKontrolKehamilanResponse>>
    */
}

class AnakInteractor(private val anakRepository: AnakRepository) :
    AnakUseCase {
    override fun getAnak(id: Int): Flow<ResultResponse<GetAnakResponse>> =
        anakRepository.getAnakResponse(id)

    override fun addAnak(anak: Anak): Flow<ResultResponse<AddAnakResponse>> =
        anakRepository.addAnakResponse(anak)

    override fun editAnak(anak: Anak): Flow<ResultResponse<EditAnakResponse>> =
        anakRepository.editAnakResponse(anak)

    override fun deleteAnak(id: String): Flow<ResultResponse<DeleteAnakResponse>> =
        anakRepository.deleteAnakResponse(id)
}

class PertumbuhanInteractor(private val pertumbuhanRepository: PertumbuhanRepository) :
    PertumbuhanUseCase {

}

class ImunisasiInteractor(private val imunisasiRepository: ImunisasiRepository) :
    ImunisasiUseCase {
    override fun getImunisasiNotDone(id: String): Flow<ResultResponse<GetImunisasiNotDoneResponse>> =
        imunisasiRepository.getImunisasiNotDoneResponse(id)

    override fun getImunisasiDone(id: String): Flow<ResultResponse<GetImunisasiDoneResponse>> =
        imunisasiRepository.getImunisasiDoneResponse(id)

    override fun getImunisasiByJenis(name: String): Flow<ResultResponse<GetImunisasiByJenisResponse>> {
        TODO("Not yet implemented")
    }

    override fun addJadwalImunisasi(id: String): Flow<ResultResponse<AddJadwalImunisasiResponse>> {
        TODO("Not yet implemented")
    }

    override fun editJadwalImunisasi(): Flow<ResultResponse<EditJadwalImunisasiResponse>> {
        TODO("Not yet implemented")
    }

}