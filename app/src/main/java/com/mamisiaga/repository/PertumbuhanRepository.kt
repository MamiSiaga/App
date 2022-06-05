package com.mamisiaga.repository

import com.mamisiaga.api.APIService
import com.mamisiaga.dataClass.Pertumbuhan
import com.mamisiaga.tools.ResultResponse
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException

class PertumbuhanRepository private constructor(private val apiService: APIService) {
    fun getPertumbuhanResponse(id: String) = flow {
        emit(ResultResponse.Loading)

        try {
            emit(ResultResponse.Success(apiService.getPertumbuhanResponse(id)))
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> emit(ResultResponse.Error("No Internet Connection"))
                else -> emit(ResultResponse.Error("Error"))
            }
        }
    }

    fun addPertumbuhanResponse(pertumbuhan: Pertumbuhan) = flow {
        emit(ResultResponse.Loading)

        try {
            //val addPertumbuhanResponse = AddPertumbuhanResponse(false, "Successful")

            //emit(ResultResponse.Success(addPertumbuhanResponse))

            emit(
                ResultResponse.Success(
                    apiService.addPertumbuhanResponse(
                        1,
                        pertumbuhan.age!!,
                        pertumbuhan.weight!!,
                        pertumbuhan.height!!,
                        pertumbuhan.headDiameter!!
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

    fun editPertumbuhanResponse(pertumbuhan: Pertumbuhan) = flow {
        emit(ResultResponse.Loading)

        try {
            /*emit(
                ResultResponse.Success(
                    apiService.editPertumbuhanResponse(
                        pertumbuhan.age!!,
                        pertumbuhan.weight!!,
                        pertumbuhan.height!!,
                        pertumbuhan.headDiameter!!
                    )
                )
            )
             */
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> emit(ResultResponse.Error("No Internet Connection"))
                else -> emit(ResultResponse.Error("Error"))
            }
        }
    }

    fun deletePertumbuhanResponse(id: String) = flow {
        emit(ResultResponse.Loading)

        try {
            emit(
                ResultResponse.Success(
                    apiService.deletePertumbuhanResponse(id)
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
        private var instance: PertumbuhanRepository? = null

        fun getInstance(apiService: APIService): PertumbuhanRepository =
            instance ?: synchronized(this) {
                instance ?: PertumbuhanRepository(apiService)
            }
    }
}