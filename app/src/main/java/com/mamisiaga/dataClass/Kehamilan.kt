package com.mamisiaga.dataClass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Kehamilan(
    val dateOfPregnancy: Date,
) : Parcelable