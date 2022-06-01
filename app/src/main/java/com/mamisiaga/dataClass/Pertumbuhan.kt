package com.mamisiaga.dataClass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pertumbuhan(
    val dateOfMeasurement: String? = null,
    val age: Int? = null,
    val weight: Int? = null,
    val height: Int? = null,
    val headDiameter: Int? = null
) : Parcelable
