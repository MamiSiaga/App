package com.mamisiaga.tools

import android.util.Patterns
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun String.withDateFormatID(): String {
    val format = SimpleDateFormat("dd-MM-yyyy", Locale("id", "ID"))
    val date = format.parse(this) as Date

    return DateFormat.getDateInstance(DateFormat.FULL).format(date)
}

fun String.withDateFormatID2(): String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale("id", "ID"))
    val date = format.parse(this)

    return DateFormat.getDateInstance(DateFormat.FULL).format(date)
}

fun convertToDate(date: String): LocalDate = LocalDate.parse(date)

fun convertToDateString(date: String): String {
    val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")

    return LocalDate.parse(date, formatter).toString()
}

fun isEmailFormat(email: String) = Patterns.EMAIL_ADDRESS.matcher(email).matches()

fun replaceString(text: String, totalN: Int) =
    if (text.length > totalN) text.replace(
        text.substring(totalN + 1, text.length),
        "..."
    )
    else text