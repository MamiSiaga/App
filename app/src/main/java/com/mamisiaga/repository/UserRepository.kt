package com.mamisiaga.repository

import com.mamisiaga.api.APIService
import com.mamisiaga.tools.ResultResponse
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException

class UserRepository (private val apiService: APIService) {

    fun getUser(token: String) = flow {
        emit(ResultResponse.Loading)

        try {
            emit(ResultResponse.Success(apiService.getUser("Bearer $token")))
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> emit(ResultResponse.Error("No internet connection"))
                else -> emit(ResultResponse.Error("Error"))
            }
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(apiService: APIService): UserRepository {
            return instance ?: synchronized(this) {
                val userRepo = UserRepository(apiService)
                instance = userRepo
                userRepo
            }
        }

    }
}