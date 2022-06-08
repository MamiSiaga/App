package com.mamisiaga.api

import retrofit2.http.*

interface APIService {
    @FormUrlEncoded
    @POST(DAFTAR)
    suspend fun daftarResponse(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("place_of_birth") placeOfBirth: String,
        @Field("date_of_birth") dateOfBirth: String,
        @Field("profile_type") profileType: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirm: String,
        @Field("device_name") device_name: String
    ): DaftarResponse

    @GET("user")
    suspend fun getUser(
        @Header("Authorization") auth: String
    ): GetUserResponse

    @FormUrlEncoded
    @POST(MASUK)
    suspend fun masukResponse(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("device_name") deviceName: String
    ): MasukResponse

    @GET("$IBU/{id}")
    suspend fun getIbuByIdResponse(
        @Path("id") id: Int
    ): GetIbuResponse

    @GET(IBU)
    suspend fun getIbuByTokenResponse(): GetIbuResponse

    @GET("$IBU/{mother_id}/$KEHAMILAN")
    suspend fun getKehamilanResponse(
        @Path("mother_id") motherId: Int
    ): GetKehamilanResponse

    @GET("$IBU/{mother_id}/$KEHAMILAN/{id}")
    suspend fun getKehamilanByIdResponse(
        @Path("mother_id") motherId: String,
        @Path("id") id: String
    ): GetKehamilanResponse

    @GET(KEHAMILAN)
    suspend fun addKehamilanResponse(
        @Field("date") date: String
    ): AddKehamilanResponse

    //Kontrol Kehamilan

    @GET(ANAK)
    suspend fun getAnakResponse(

    ): GetAnakResponse

    @GET("$ANAK/{id}")
    suspend fun getAnakByIdResponse(
        @Path("mother_id") motherId: String,
        @Path("id") childrenId: String
    ): GetAnakResponse

    @FormUrlEncoded
    @POST(ANAK)
    suspend fun addAnakResponse(
        @Field("name") name: String,
        @Field("date_of_birth") dateOfBirth: String,
        @Field("place_of_birth") placeOfBirth: String,
        @Field("sex") sex: Int,
        @Field("blood_type") bloodType: String,
    ): AddAnakResponse

    @FormUrlEncoded
    @PUT("$ANAK/{id}")
    suspend fun editAnakResponse(
        @Path("id") id: Int,
        @Field("date_of_birth") dateOfBirth: String,
        @Field("place_of_birth") placeOfBirth: String,
        @Field("sex") sex: Int,
        @Field("blood_type") bloodType: String
    ): EditAnakResponse

    @FormUrlEncoded
    @DELETE("$ANAK/{id}")
    suspend fun deleteAnakResponse(
        @Path("id") id: String
    ): DeleteAnakResponse

    @GET("$ANAK/{children_id}/$PERTUMBUHAN")
    suspend fun getPertumbuhanResponse(
        @Path("children_id") childrenId: Int
    ): GetPertumbuhanResponse

    @FormUrlEncoded
    @POST("$ANAK/{children_id}/$PERTUMBUHAN")
    suspend fun addPertumbuhanResponse(
        @Path("children_id") childrenId: Int,
        @Field("age_in_months") age: Int,
        @Field("height_in_cm") height: Int,
        @Field("weight_in_kg") weight: Int,
        @Field("head_circumference_in_cm") headDiameter: Int
    ): AddPertumbuhanResponse

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

    @GET("$ANAK/{children_id}/$IMUNISASI")
    suspend fun getImunisasiResponse(
        @Path("children_id") childrenId: Int
    ): GetImunisasiResponse

    companion object {
        private const val DAFTAR = "register"
        private const val MASUK = "login"
        private const val IBU = "users"
        private const val KEHAMILAN = "pregnancies"
        private const val KONTROL_KEHAMILAN = "anthropometries"
        private const val ANAK = "childrens"
        private const val PERTUMBUHAN = "body-mass-indices"
        private const val IMUNISASI = "immunizations"
    }
}