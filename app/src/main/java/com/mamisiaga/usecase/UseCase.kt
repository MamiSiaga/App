package com.mamisiaga.usecase

import androidx.lifecycle.LiveData
import com.mamisiaga.`class`.AnakEdit
import com.mamisiaga.`class`.AnakTambah
import com.mamisiaga.`class`.Ibu
import com.mamisiaga.`class`.IbuDaftar
import com.mamisiaga.api.*
import com.mamisiaga.tools.ResultResponse
import kotlinx.coroutines.flow.Flow

interface IbuPreferenceUseCase {
    fun getIbu(): LiveData<Ibu>

    suspend fun masuk(ibu: Ibu)

    suspend fun keluar()
}

interface AutentikasiUseCase {
    fun daftar(
        ibuDaftar: IbuDaftar
    ): Flow<ResultResponse<DaftarResponse>>

    fun masuk(email: String, kataSandi: String): Flow<ResultResponse<MasukResponse>>
}

interface IbuUseCase {
    fun getIbu(): Flow<ResultResponse<GetIbuResponse>>
}

interface KehamilanUseCase {
    //fun getKehamilan(id: String): Flow<ResultResponse<GetKehamilanResponse>>

    fun getKontrolKehamilan(id: String): Flow<ResultResponse<GetKontrolKehamilanResponse>>

    //fun addJadwalKontrolKehamilan(): Flow<ResultResponse<AddKontrolKehamilanResponse>>

    //fun editJadwalKontrolKehamilan(): Flow<ResultResponse<AddKontrolKehamilanResponse>>

    //fun getInformasiKontrolKehamilan(): Flow<ResultResponse<AddKontrolKehamilanResponse>>
}

interface AnakUseCase {
    fun getAnak(id: String): Flow<ResultResponse<GetAnakResponse>>

    fun addAnak(anakTambah: AnakTambah): Flow<ResultResponse<AddAnakResponse>>

    fun editAnak(anakEdit: AnakEdit): Flow<ResultResponse<EditAnakResponse>>

    fun deleteAnak(id: String): Flow<ResultResponse<DeleteAnakResponse>>
}

interface ImunisasiUseCase {
    fun getImunisasiNotDone(id: String): Flow<ResultResponse<GetImunisasiNotDoneResponse>>

    fun getImunisasiDone(id: String): Flow<ResultResponse<GetImunisasiDoneResponse>>

    fun getImunisasiByJenis(name: String): Flow<ResultResponse<GetImunisasiByJenisResponse>>

    fun addJadwalImunisasi(id: String): Flow<ResultResponse<AddJadwalImunisasiResponse>>

    fun editJadwalImunisasi(): Flow<ResultResponse<EditJadwalImunisasiResponse>>
}