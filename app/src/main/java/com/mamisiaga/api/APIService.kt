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

    @GET("$IBU/{id}")
    suspend fun getIbuResponse(
        @Path("id") id: String
    ): GetIbuResponse

    @GET("employees")
    suspend fun getAnakResponse(
        /*@Query("id") id: String*/
    ): GetAnakResponse

    @FormUrlEncoded
    @POST("create")
    suspend fun addAnakResponse(
        @Field("name") name: String,
        @Field("salary") salary: String,
        @Field("age") age: String
        /*@Field("nik") nik: String,
        @Field("dateOfBirth") dateOfBirth: String,
        @Field("gender") gender: String,
        @Field("bloodType") blood: String,
        @Field("weight") weight: Double,
        @Field("height") height: Double,
        @Field("headDiameter") headDiameter: Double
         */
    ): AddAnakResponse

    @FormUrlEncoded
    //@PUT(MASUK) // not sure
    suspend fun editAnakResponse(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("dateOfBirth") dateOfBirth: String,
        @Field("gender") gender: String,
        @Field("bloodType") blood: String,
        @Field("weight") weight: Double,
        @Field("height") height: Double,
        @Field("headDiameter") headDiameter: Double
    ): EditAnakResponse

    @FormUrlEncoded
    @DELETE("delete")
    suspend fun deleteAnakResponse(
        @Field("id") id: String
    ): DeleteAnakResponse

    @GET("$PERTUMBUHAN/")
    suspend fun getPertumbuhanResponse(
        /*@Query("id") id: String*/
    ): GetAnakResponse

    @FormUrlEncoded
    @POST("create")
    suspend fun addPertumbuhanResponse(
        @Field("age_in_months") age: Int,
        @Field("height_in_cm") height: Int,
        @Field("weight_in_kg") weight: Int,
        @Field("head_circumference_in_cm") headDiameter: Int
    ): AddAnakResponse

    @FormUrlEncoded
    //@PUT(MASUK) // not sure
    suspend fun editPertumbuhanResponse(
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
    @DELETE("delete")
    suspend fun deletePertumbuhanResponse(
        @Field("id") id: String
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