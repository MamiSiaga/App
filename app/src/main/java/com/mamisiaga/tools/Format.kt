package com.mamisiaga.tools

import android.util.Patterns
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun String.withDateFormat(): String {
    val format = SimpleDateFormat("dd-MM-yyyy", Locale("id", "ID"))
    val date = format.parse(this) as Date

    return DateFormat.getDateInstance(DateFormat.FULL).format(date)
}

fun convertToDate(date: String): Date {
    val format = SimpleDateFormat("dd-MM-yyyy", Locale("id", "ID"))

    return format.parse(date) as Date
}

fun isEmailFormat(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

fun replaceString(text: String, totalN: Int) =
    if (text.length > totalN) text.replace(
        text.substring(totalN + 1, text.length),
        "..."
    )
    else text