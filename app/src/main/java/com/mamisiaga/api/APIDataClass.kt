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

data class GetAnakResponse(
    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("anakData")
    val anakData: List<AnakData>
)

data class AnakData(
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("dateOfBirth")
    val dateOfBirth: String
)