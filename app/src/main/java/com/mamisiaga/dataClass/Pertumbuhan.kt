package com.mamisiaga.dataClass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pertumbuhan(
    val id: Int?,
    val childrenId: Int?,
    val dateOfMeasurement: String?,
    var age: Int?,
    var weight: Int?,
    val height: Int?,
    val headDiameter: Int?
) : Parcelable
