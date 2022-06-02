package com.mamisiaga.api

import com.google.gson.annotations.SerializedName
import java.util.*

data class DaftarResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class MasukResponse(
    @field:SerializedName("data")
    val masukData: MasukData
)

data class MasukData(
    /*@field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("email")
    val email: String,
     */

    @field:SerializedName("token")
    val token: String
)

data class GetEmailResponse(
    /*@field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("email")
    val email: String,
     */

    @field:SerializedName("token")
    val token: String
)

// PasswordResetLink Result
data class CekEmailResponse(
    @field:SerializedName("status")
    val status: String
)

data class GetIbuResponse(
    @field:SerializedName("data")
    val ibuData: IbuData
)

data class IbuData(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("date_of_birth")
    val dateOfBirth: Date
)

data class GetKehamilanResponse(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("date")
    val date: String
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

    @field:SerializedName("data")
    val anakData: List<AnakData>
)

data class AnakData(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("date_of_birth")
    val dateOfBirth: Date,

    @field:SerializedName("place_of_birth")
    val placeOfBirth: String,

    @field:SerializedName("blood_type")
    val bloodType: String
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
    @field:SerializedName("status")
    val status: String
)

data class GetPertumbuhanResponse(
    @field:SerializedName("data")
    val pertumbuhanData: List<PertumbuhanData>
)

data class PertumbuhanData(
    @field:SerializedName("age_in_months")
    val age: Int,

    @field:SerializedName("weight_in_kg")
    val weight: Int,

    @field:SerializedName("height_in_cm")
    val height: Int,

    @field:SerializedName("head_circumference_in_cm")
    val headDiameter: Int
)

data class AddPertumbuhanResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class EditPertumbuhanResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

data class DeletePertumbuhanResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
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