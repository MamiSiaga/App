package com.mamisiaga.api

import retrofit2.http.*
import java.util.*

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

    @GET("$IBU/{id}")
    suspend fun getIbuResponse(
        @Path("id") id: String
    ): GetIbuResponse

    @GET("$IBU/{mother_id}/$KEHAMILAN")
    suspend fun getKehamilanResponse(
        @Path("mother_id") motherId: String
    ): GetKehamilanResponse

    @GET("$IBU/{mother_id}/$KEHAMILAN/{id}")
    suspend fun getKehamilanByIdResponse(
        @Path("mother_id") motherId: String,
        @Path("id") id: String
    ): GetKehamilanResponse

    //KontrolKehamilan

    @GET("$IBU/{mother_id}/$ANAK/{id}")
    suspend fun getAnakResponse(
        @Path("mother_id") motherId: String,
        @Path("id") childrenId: String
    ): GetAnakResponse

    @FormUrlEncoded
    @POST(ANAK)
    suspend fun addAnakResponse(
        @Field("name") name: String,
        @Field("date_of_birth") dateOfBirth: Date,
        @Field("place_of_birth") placeOfBirth: String,
        @Field("blood_type") bloodType: String,
    ): AddAnakResponse

    @FormUrlEncoded
    @PUT("$ANAK/{id}")
    suspend fun editAnakResponse(
        @Path("id") id: String,
        @Field("date_of_birth") dateOfBirth: Date,
        @Field("place_of_birth") placeOfBirth: String,
        @Field("blood_type") bloodType: String
    ): EditAnakResponse

    @FormUrlEncoded
    @DELETE("$ANAK/{id}")
    suspend fun deleteAnakResponse(
        @Path("id") id: String
    ): DeleteAnakResponse

    @GET("$ANAK/{children_id}/$PERTUMBUHAN")
    suspend fun getPertumbuhanResponse(
        @Path("children_id") childrenId: String
    ): GetAnakResponse

    @FormUrlEncoded
    @POST("$ANAK/{children_id}/$PERTUMBUHAN")
    suspend fun addPertumbuhanResponse(
        @Path("children_id") childrenId: Int,
        @Field("age_in_months") age: Int,
        @Field("height_in_cm") height: Int,
        @Field("weight_in_kg") weight: Int,
        @Field("head_circumference_in_cm") headDiameter: Int
    ): AddAnakResponse

    @FormUrlEncoded
    @PUT("$ANAK/{children_id}/$PERTUMBUHAN/{id}")
    suspend fun editPertumbuhanResponse(
        @Path("children_id") childrenId: Int,
        @Path("id") id: Int,
        @Field("age_in_months") age: Int,
        @Field("height_in_cm") height: Int,
        @Field("weight_in_kg") weight: Int,
        @Field("head_circumference_in_cm") headDiameter: Int
    ): EditPertumbuhanResponse

    @FormUrlEncoded
    @DELETE("delete")
    suspend fun deletePertumbuhanResponse(
        @Field("$ANAK/{children_id}/$PERTUMBUHAN/{id}") id: String
    ): DeleteAnakResponse

    companion object {
        private const val DAFTAR = "register"
        private const val MASUK = "login"
        private const val IBU = "mothers"
        private const val KEHAMILAN = "pregnancies"
        private const val KONTROL_KEHAMILAN = "anthropometries"
        private const val ANAK = "childrens"
        private const val PERTUMBUHAN = "body-mass-indices"
        private const val IMUNISASI = "immunizations"
    }
}