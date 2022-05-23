package com.mamisiaga.repository

import com.mamisiaga.`class`.IbuDaftar
import com.mamisiaga.api.APIService
import com.mamisiaga.tools.ResultResponse
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException

class AutentikasiRepository private constructor(private val apiService: APIService) {
    fun daftar(ibuDaftar: IbuDaftar) = flow {
        emit(ResultResponse.Loading)

        try {
            emit(
                ResultResponse.Success(
                    apiService.daftarResponse(
                        ibuDaftar.name,
                        ibuDaftar.email,
                        ibuDaftar.password
                    )
                )
            )
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> emit(ResultResponse.Error("No Internet Connection"))
                else -> emit(ResultResponse.Error("Error"))
            }
        }
    }

    fun masuk(email: String, kataSandi: String) = flow {
        emit(ResultResponse.Loading)

        try {
            emit(ResultResponse.Success(apiService.masukResponse(email, kataSandi)))
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> emit(ResultResponse.Error("No Internet Connection"))
                else -> emit(ResultResponse.Error("Error"))
            }
        }
    }

    companion object {
        @Volatile
        private var instance: AutentikasiRepository? = null

        fun getInstance(apiService: APIService): AutentikasiRepository =
            instance ?: synchronized(this) {
                instance ?: AutentikasiRepository(apiService)
            }
    }
}