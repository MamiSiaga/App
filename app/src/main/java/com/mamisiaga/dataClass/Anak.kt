package com.mamisiaga.dataClass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Anak(
    val id: Int?,
    val motherId: Int?,
    val name: String?,
    val dateOfBirth: String?,
    val placeOfBirth: String?,
    val sex: Int?,
    val bloodType: String?
) : Parcelable