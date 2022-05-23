package com.mamisiaga.`class`

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Anak(
    val id: String,
    val name: String,
    val dateOfBirth: String
) : Parcelable

@Parcelize
data class AnakTambah(
    val name: String,
    val nik: String,
    val dateOfBirth: String,
    val gender: String,
    val bloodType: String,
    val weight: Double,
    val height: Double,
    val headDiameter: Double
) : Parcelable

@Parcelize
data class AnakEdit(
    val id: String,
    val name: String,
    val nik: String,
    val dateOfBirth: String,
    val gender: String,
    val bloodType: String,
    val weight: Double,
    val height: Double,
    val headDiameter: Double
) : Parcelable