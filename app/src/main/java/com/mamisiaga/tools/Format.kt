package com.mamisiaga.tools

import android.util.Patterns
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.withDateFormat(): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
    val date = format.parse(this) as Date

    return DateFormat.getDateInstance(DateFormat.FULL).format(date)
}

fun isEmailFormat(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

fun replaceString(text: String, totalN: Int) =
    if (text.length > totalN) text.replace(
        text.substring(totalN + 1, text.length),
        "..."
    )
    else text