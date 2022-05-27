package com.mamisiaga.repository

import com.mamisiaga.`class`.Anak
import com.mamisiaga.api.APIService
import com.mamisiaga.api.AnakData
import com.mamisiaga.tools.ResultResponse
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException

class AnakRepository private constructor(private val apiService: APIService) {
    private val listAnakData = mutableListOf<AnakData>()

    fun getAnakResponse(id: String) = flow {
        emit(ResultResponse.Loading)

        try {
            /*listAnakData.add(AnakData("1", "aa", "20-01-2022"))
            listAnakData.add(AnakData("2", "ab", "20-02-2022"))

            val getAnakResponse = GetAnakResponse(false, "successful", listAnakData.toList())

            emit(ResultResponse.Success(getAnakResponse))

             */

            emit(ResultResponse.Success(apiService.getAnakResponse(/*id*/)))
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
            //val addAnakResponse = AddAnakResponse(false, "Successful")

            //emit(ResultResponse.Success(addAnakResponse))

            emit(
                ResultResponse.Success(
                    apiService.addAnakResponse(
                        anak.name!!,
                        /*
                        anak.nik!!,
                        anak.dateOfBirth!!,
                        anak.gender!!,
                        anak.bloodType!!,
                        anak.weight!!,
                        anak.height!!,
                        anak.headDiameter!!*/
                        "1234", "22"
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
                        anak.name!!,
                        anak.nik!!,
                        anak.dateOfBirth!!,
                        anak.gender!!,
                        anak.bloodType!!,
                        anak.weight!!,
                        anak.height!!,
                        anak.headDiameter!!
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