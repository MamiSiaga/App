package com.mamisiaga.tools

fun keepNumbers(text: String): String{
    val keep="[^0-9 ]".toRegex()

    return keep.replace(text, "")
}