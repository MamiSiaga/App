package com.mamisiaga.repository

import androidx.lifecycle.liveData
import com.mamisiaga.api.APIService
import com.mamisiaga.tools.ResultResponse
import java.net.UnknownHostException

class AnakRepository private constructor(private val apiService: APIService) {
    fun getAnakResponse(id: String) = liveData {
        emit(ResultResponse.Loading)

        try {
            emit(ResultResponse.Success(apiService.getAnakResponse(id)))
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> emit(ResultResponse.Error("No Internet Connection"))
                else -> emit(ResultResponse.Error("Error"))
            }
        }
    }
    
    companion object {
        @Volatile
        private var instance: AnakRepository? = null

        fun getInstance(apiService: APIService): AnakRepository =
            instance ?: synchronized(this) {
                instance ?: AnakRepository(apiService)
            }
    }
}