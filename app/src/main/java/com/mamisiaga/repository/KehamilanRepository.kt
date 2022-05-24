package com.mamisiaga.repository

import com.mamisiaga.api.*
import com.mamisiaga.tools.ResultResponse
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException

class KehamilanRepository private constructor(private val apiService: APIService) {
    private val listKontrolKehamilanData = mutableListOf<KontrolKehamilanData>()

    fun addRencanaKehamilanResponse() = flow {
        emit(ResultResponse.Loading)

        try {
            //emit(ResultResponse.Success(apiService.getKehamilanResponse(id)))
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> emit(ResultResponse.Error("No Internet Connection"))
                else -> emit(ResultResponse.Error("Error"))
            }
        }
    }

    fun getKontrolKehamilanResponse(id: String) = flow {
        emit(ResultResponse.Loading)

        try {
            listKontrolKehamilanData.add(KontrolKehamilanData("1", "20-01-2022"))
            listKontrolKehamilanData.add(KontrolKehamilanData("2", "20-02-2022"))

            val getKontrolKehamilanResponse =
                GetKontrolKehamilanResponse(false, "successful", listKontrolKehamilanData.toList())

            emit(ResultResponse.Success(getKontrolKehamilanResponse))

            //emit(ResultResponse.Success(apiService.getKehamilanResponse(id)))
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> emit(ResultResponse.Error("No Internet Connection"))
                else -> emit(ResultResponse.Error("Error"))
            }
        }
    }

    fun submitKontrolKehamilanResponse() = flow {
        emit(ResultResponse.Loading)

        try {
            //emit(ResultResponse.Success(apiService.submitKontrolKehamilanResponse(id)))
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> emit(ResultResponse.Error("No Internet Connection"))
                else -> emit(ResultResponse.Error("Error"))
            }
        }
    }

    fun addRencanaMelahirkanResponse(id: String) = flow {
        emit(ResultResponse.Loading)

        try {
            //emit(ResultResponse.Success(apiService.addRencanaMelahirkanResponse(id)))
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> emit(ResultResponse.Error("No Internet Connection"))
                else -> emit(ResultResponse.Error("Error"))
            }
        }
    }

    companion object {
        @Volatile
        private var instance: KehamilanRepository? = null

        fun getInstance(apiService: APIService): KehamilanRepository =
            instance ?: synchronized(this) {
                instance ?: KehamilanRepository(apiService)
            }
    }
}