package com.mamisiaga.`class`

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ibu(
    val name: String,
    val email: String,
    val id: String,
    val isMasuk: Boolean
) : Parcelable

@Parcelize
data class IbuDaftar(
    val name: String,
    val email: String,
    val password: String,
    val dateOfBirth: String,
    val type: String,
    val isParticipatingPosyandu: Boolean,
    val posyandu: String?
) : Parcelable