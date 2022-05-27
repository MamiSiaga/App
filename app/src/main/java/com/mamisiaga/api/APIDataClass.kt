package com.mamisiaga.api

import com.google.gson.annotations.SerializedName

data class DaftarResponse(
    @field:SerializedName("error")
    val error: Boolean,
    @field:SerializedName("message")
    val message: String
)

data class MasukResponse(
    @field:SerializedName("masukResult")
    val masukResult: MasukData,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class MasukData(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("email")
    val email: String
)

data class GetIbuResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class AddKehamilanResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class EditKehamilanResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class DeleteKehamilanResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class GetKontrolKehamilanResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("kontrolKehamilanData")
    val kontrolKehamilanData: List<KontrolKehamilanData>
)

data class KontrolKehamilanData(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("date")
    val date: String?
)

data class GetKontrolKehamilanDoneResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class SubmitKontrolKehamilanNotDoneResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class GetKontrolKehamilanNotDoneResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class GetInformasiKontrolKehamilanResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class GetAnakResponse(
    /*
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,
    */

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("data")
    val anakData: List<AnakData>
)

data class AnakData(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("employee_name")
    val name: String

    //@field:SerializedName("dateOfBirth")
    //val dateOfBirth: String
)

data class AddAnakResponse(
    //@field:SerializedName("error")
    //val error: Boolean,

    @field:SerializedName("status")
    val status: String
)

data class EditAnakResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class DeleteAnakResponse(
    //@field:SerializedName("error")
    //val error: Boolean,

    @field:SerializedName("status")
    val status: String
)

data class GetPertumbuhanResponse(
    /*
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,
    */

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("data")
    val pertumbuhanData: List<PertumbuhanData>
)

data class PertumbuhanData(
    @field:SerializedName("date_of_measurement")
    val date_of_measurement: String,

    @field:SerializedName("age")
    val age: Int,

    @field:SerializedName("weight")
    val weight: Double,

    @field:SerializedName("height")
    val height: Double,

    @field:SerializedName("head_diameter")
    val headDiameter: Double
)

data class GetImunisasiNotDoneResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("imunisasiNotDoneData")
    val imunisasiNotDoneData: List<ImunisasiNotDoneData>
)

data class ImunisasiNotDoneData(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("dateOfBirth")
    val date: String?
)

data class GetImunisasiDoneResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("imunisasiDoneData")
    val imunisasiDoneData: List<ImunisasiDoneData>
)

data class ImunisasiDoneData(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("dateOfBirth")
    val date: String
)

data class GetImunisasiByJenisResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("imunisasiByJenisData")
    val imunisasiByJenisData: List<ImunisasiByJenisData>
)

data class ImunisasiByJenisData(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("dateOfBirth")
    val date: String
)

data class AddJadwalImunisasiResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class EditJadwalImunisasiResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class GetKMSResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class KMSData(
    @field:SerializedName("month")
    val error: Boolean,

    @field:SerializedName("weight")
    val message: String
)

data class AddKMSResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class EditKMSResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class DeleteKMSResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)