package com.mamisiaga.dataClass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ibu(
    //val name: String,
    //val email: String,
    val id: Int? = null,
    val token: String? = null,
    val isMasuk: Boolean? = null
) : Parcelable

@Parcelize
data class IbuDaftar(
    val name: String,
    val email: String,
    val password: String,
    val password_confirm: String,
    val placeOfBirth: String,
    val dateOfBirth: String,
    val profileType: String,
) : Parcelable