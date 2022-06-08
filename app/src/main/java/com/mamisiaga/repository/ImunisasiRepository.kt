package com.mamisiaga.repository

import com.mamisiaga.api.*
import com.mamisiaga.tools.ResultResponse
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException

class ImunisasiRepository private constructor(private val apiService: APIService) {
    private val listImunisasiNotDoneData = mutableListOf<ImunisasiNotDoneData>()
    private val listImunisasiDoneData = mutableListOf<ImunisasiDoneData>()

    fun getImunisasiNotDoneResponse(id: String) = flow {
        emit(ResultResponse.Loading)

        try {
            listImunisasiNotDoneData.add(ImunisasiNotDoneData("1", "HIV", "20-01-2022"))
            listImunisasiNotDoneData.add(ImunisasiNotDoneData("2", "HIV2", "20-02-2022"))

            val getImunisasiResponse = GetImunisasiNotDoneResponse(false, "successful", listImunisasiNotDoneData.toList())

            emit(ResultResponse.Success(getImunisasiResponse))

            //emit(ResultResponse.Success(apiService.getImunisasiResponse(id)))
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> emit(ResultResponse.Error("No Internet Connection"))
                else -> emit(ResultResponse.Error("Error"))
            }
        }
    }

    fun getImunisasiDoneResponse(id: String) = flow {
        emit(ResultResponse.Loading)

        try {
            listImunisasiDoneData.add(ImunisasiDoneData("0", "--", "20-01-2022"))
            listImunisasiDoneData.add(ImunisasiDoneData("0.0", "---", "20-02-2022"))

            val getImunisasiResponse = GetImunisasiDoneResponse(false, "successful", listImunisasiDoneData.toList())

            emit(ResultResponse.Success(getImunisasiResponse))

            //emit(ResultResponse.Success(apiService.getImunisasiResponse(id)))
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> emit(ResultResponse.Error("No Internet Connection"))
                else -> emit(ResultResponse.Error("Error"))
            }
        }
    }

    fun getImunisasiResponse(id: Int) = flow {
        emit(ResultResponse.Loading)

        try {
            emit(ResultResponse.Success(apiService.getImunisasiResponse(id)))
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> emit(ResultResponse.Error("No Internet Connection"))
                else -> emit(ResultResponse.Error("Error"))
            }
        }
    }

    /*
    fun addImunisasiResponse(imunisasiTambah: ImunisasiTambah) = flow {
        emit(ResultResponse.Loading)

        try {
            val addImunisasiResponse = AddImunisasiResponse(false, "Successful")

            emit(ResultResponse.Success(addImunisasiResponse))

            /*
            emit(
                ResultResponse.Success(
                    apiService.addImunisasiResponse(
                        anakTambah.name,
                        anakTambah.nik,
                        anakTambah.dateOfBirth,
                        anakTambah.gender,
                        anakTambah.bloodType,
                        anakTambah.weight,
                        anakTambah.height,
                        anakTambah.headDiameter
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

    fun editImunisasiResponse(anakEdit: ImunisasiEdit) = flow {
        emit(ResultResponse.Loading)

        try {
            emit(
                ResultResponse.Success(
                    apiService.editImunisasiResponse(
                        anakEdit.id,
                        anakEdit.name,
                        anakEdit.nik,
                        anakEdit.dateOfBirth,
                        anakEdit.gender,
                        anakEdit.bloodType,
                        anakEdit.weight,
                        anakEdit.height,
                        anakEdit.headDiameter
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

    fun deleteImunisasiResponse(id: String) = flow {
        emit(ResultResponse.Loading)

        try {
            emit(
                ResultResponse.Success(
                    apiService.deleteImunisasiResponse(id)
                )
            )
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException -> emit(ResultResponse.Error("No Internet Connection"))
                else -> emit(ResultResponse.Error("Error"))
            }
        }
    }
     */

    companion object {
        @Volatile
        private var instance: ImunisasiRepository? = null

        fun getInstance(apiService: APIService): ImunisasiRepository =
            instance ?: synchronized(this) {
                instance ?: ImunisasiRepository(apiService)
            }
    }
}