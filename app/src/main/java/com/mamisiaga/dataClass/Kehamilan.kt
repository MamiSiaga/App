package com.mamisiaga.dataClass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Kehamilan(
    val tglHamil: Double,
) : Parcelable