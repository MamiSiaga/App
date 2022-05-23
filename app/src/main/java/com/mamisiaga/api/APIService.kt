package com.mamisiaga.api

import retrofit2.http.*

interface APIService {
    @FormUrlEncoded
    @POST(DAFTAR)
    suspend fun daftarResponse(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): DaftarResponse

    @FormUrlEncoded
    @POST(MASUK)
    suspend fun masukResponse(
        @Field("email") email: String,
        @Field("password") password: String
    ): MasukResponse

    @GET(IBU)
    suspend fun getIbuResponse(
        @Query("id") id: String,
        @Query("email") email: String
    ): GetIbuResponse

    @GET(ANAK)
    suspend fun getAnakResponse(
        @Query("id") id: String
    ): GetAnakResponse

    @FormUrlEncoded
    //@POST(MASUK)
    suspend fun addAnakResponse(
        @Field("name") name: String,
        @Field("nik") nik: String,
        @Field("dateOfBirth") dateOfBirth: String,
        @Field("gender") gender: String,
        @Field("bloodType") blood: String,
        @Field("weight") weight: Double,
        @Field("height") height: Double,
        @Field("headDiameter") headDiameter: Double,
    ): AddAnakResponse

    @FormUrlEncoded
    //@PUT(MASUK) // not sure
    suspend fun editAnakResponse(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("nik") nik: String,
        @Field("dateOfBirth") dateOfBirth: String,
        @Field("gender") gender: String,
        @Field("bloodType") blood: String,
        @Field("weight") weight: Double,
        @Field("height") height: Double,
        @Field("headDiameter") headDiameter: Double
    ): EditAnakResponse

    @FormUrlEncoded
    //@DELETE(MASUK)
    suspend fun deleteAnakResponse(
        @Field("id") id: String
    ): DeleteAnakResponse

    companion object {
        private const val DAFTAR = "daftar"
        private const val MASUK = "masuk"
        private const val IBU = "ibu"
        private const val ANAK = "anak"
    }
}