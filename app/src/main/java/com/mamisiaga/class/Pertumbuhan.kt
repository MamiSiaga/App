package com.mamisiaga.`class`

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pertumbuhan(
    val dateOfMeasurement: String,
    val age: Int,
    val weight: Double,
    val height: Double,
    val headDiameter: Double
) : Parcelable
