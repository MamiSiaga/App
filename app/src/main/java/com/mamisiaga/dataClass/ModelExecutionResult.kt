package com.mamisiaga.dataClass

import android.graphics.Bitmap

data class ModelExecutionResult(
    val bitmapResult: Bitmap,
    val executionLog: String,
    val itemsFound: Map<String, Int>
)
