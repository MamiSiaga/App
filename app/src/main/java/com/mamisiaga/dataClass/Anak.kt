package com.mamisiaga.dataClass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Anak(
    val id: String?,
    val name: String?,
    val dateOfBirth: Date?,
    val placeOfBirth: String?,
    val bloodType: String?
) : Parcelable