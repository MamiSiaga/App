package com.mamisiaga.dataClass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Imunisasi(
    val id: String?,
    val name: String?
) : Parcelable
