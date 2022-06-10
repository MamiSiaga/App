package com.mamisiaga.dataClass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Kehamilan(
    val id: Int?,
    val motherId: Int?,
    val dateOfPregnancy: String?,
    val pregnancyTotal: Int?
) : Parcelable