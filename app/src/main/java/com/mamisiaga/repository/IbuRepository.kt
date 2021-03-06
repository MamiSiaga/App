package com.mamisiaga.repository

import com.mamisiaga.api.APIService
import com.mamisiaga.tools.ResultResponse
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException

class IbuRepository private constructor(private val apiService: APIService) {
    fun getIbuByIdResponse(id: Int) = flow {
        emit(ResultResponse.Loading)

        try {
            emit(ResultResponse.Success(apiService.getIbuByIdResponse(id)))
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> emit(ResultResponse.Error("No Internet Connection"))
                else -> emit(ResultResponse.Error(e.message.toString()))
            }
        }
    }

    fun getIbuByTokenResponse() = flow {
        emit(ResultResponse.Loading)

        try {
            emit(ResultResponse.Success(apiService.getIbuByTokenResponse()))
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> emit(ResultResponse.Error("No Internet Connection"))
                else -> emit(ResultResponse.Error(e.message.toString()))
            }
        }
    }

    companion object {
        @Volatile
        private var instance: IbuRepository? = null

        fun getInstance(apiService: APIService): IbuRepository =
            instance ?: synchronized(this) {
                instance ?: IbuRepository(apiService)
            }
    }
}
