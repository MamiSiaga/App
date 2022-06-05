package com.mamisiaga.dataClass

data class Recognition(
    var id: String = "",
    var title: String = "",
    var confidence: Float = 0F
) {
    override fun toString(): String {
        return "Title = $title, Confidence = $confidence)"
    }
}