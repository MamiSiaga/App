package com.mamisiaga.repository

import com.mamisiaga.api.APIService
import com.mamisiaga.api.AnakData
import com.mamisiaga.dataClass.Anak
import com.mamisiaga.tools.ResultResponse
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException

class AnakRepository private constructor(private val apiService: APIService) {
    private val listAnakData = mutableListOf<AnakData>()

    fun getAnakResponse(id: String) = flow {
        try {
            //emit(ResultResponse.Success(apiService.getAnakResponse(/*motherId, id*/)))
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> emit(ResultResponse.Error("No Internet Connection"))
                else -> emit(ResultResponse.Error("Error"))
            }
        }
    }

    fun addAnakResponse(anak: Anak) = flow {
        emit(ResultResponse.Loading)

        try {
            emit(
                ResultResponse.Success(
                    apiService.addAnakResponse(
                        anak.name!!,
                        anak.dateOfBirth!!,
                        anak.placeOfBirth!!,
                        anak.bloodType!!
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

    fun editAnakResponse(anak: Anak) = flow {
        emit(ResultResponse.Loading)

        try {
            emit(
                ResultResponse.Success(
                    apiService.editAnakResponse(
                        anak.id!!,
                        anak.dateOfBirth!!,
                        anak.placeOfBirth!!,
                        anak.bloodType!!
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

    fun deleteAnakResponse(id: String) = flow {
        emit(ResultResponse.Loading)

        try {
            emit(
                ResultResponse.Success(
                    apiService.deleteAnakResponse(id)
                )
            )
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