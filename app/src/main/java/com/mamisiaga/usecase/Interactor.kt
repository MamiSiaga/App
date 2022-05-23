package com.mamisiaga.usecase

import com.mamisiaga.`class`.AnakEdit
import com.mamisiaga.`class`.AnakTambah
import com.mamisiaga.`class`.IbuDaftar
import com.mamisiaga.api.*
import com.mamisiaga.repository.*
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

    /*
    override fun getKontrolKehamilanNotDone(): Flow<ResultResponse<GetKontrolKehamilanNotDoneResponse>>

    override fun getKontrolKehamilanDone(): Flow<ResultResponse<GetKontrolKehamilanDoneResponse>>
     */
}

class KehamilanInteractor(private val kehamilanRepository: KehamilanRepository) : KehamilanUseCase {
    override fun getKontrolKehamilan(id: String): Flow<ResultResponse<GetKontrolKehamilanResponse>> =
        kehamilanRepository.getKontrolKehamilanResponse(id)

    /*
    override fun getKontrolKehamilanNotDone(): Flow<ResultResponse<GetKontrolKehamilanNotDoneResponse>>

    override fun getKontrolKehamilanDone(): Flow<ResultResponse<GetKontrolKehamilanDoneResponse>>

    override fun addKontrolKehamilan(): Flow<ResultResponse<AddKontrolKehamilanResponse>>
    */
}

class AnakInteractor(private val anakRepository: AnakRepository) :
    AnakUseCase {
    override fun getAnak(id: String): Flow<ResultResponse<GetAnakResponse>> =
        anakRepository.getAnakResponse(id)

    override fun addAnak(anakTambah: AnakTambah): Flow<ResultResponse<AddAnakResponse>> =
        anakRepository.addAnakResponse(anakTambah)

    override fun editAnak(anakEdit: AnakEdit): Flow<ResultResponse<EditAnakResponse>> =
        anakRepository.editAnakResponse(anakEdit)

    override fun deleteAnak(id: String): Flow<ResultResponse<DeleteAnakResponse>> =
        anakRepository.deleteAnakResponse(id)
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