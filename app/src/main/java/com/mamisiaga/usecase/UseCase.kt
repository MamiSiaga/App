package com.mamisiaga.usecase

import androidx.lifecycle.LiveData
import com.mamisiaga.`class`.Ibu
import com.mamisiaga.`class`.IbuDaftar
import com.mamisiaga.api.DaftarResponse
import com.mamisiaga.api.GetAnakResponse
import com.mamisiaga.api.GetIbuResponse
import com.mamisiaga.api.MasukResponse
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

interface AnakUseCase {
    fun getAnak(): Flow<ResultResponse<GetAnakResponse>>
}