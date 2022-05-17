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

    companion object {
        private const val DAFTAR = "daftar"
        private const val MASUK = "masuk"
        private const val IBU = "ibu"
        private const val ANAK = "anak"
    }
}


