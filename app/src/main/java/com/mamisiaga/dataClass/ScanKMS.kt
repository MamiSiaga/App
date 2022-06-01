package com.mamisiaga.dataClass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ScanKMS(
    val dateOfMeasurement: String,
    val weight: Double,
    val height: Double?,
    val headDiameter: Double?
) : Parcelable