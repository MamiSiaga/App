package com.mamisiaga.dataClass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ibu(
    //val name: String,
    //val email: String,
    val id: Int,
    val token: String,
    val isMasuk: Boolean
) : Parcelable

@Parcelize
data class IbuDaftar(
    val name: String,
    val email: String,
    val password: String,
    val password_confirm: String,
    val dateOfBirth: String,
    val type: String,
) : Parcelable