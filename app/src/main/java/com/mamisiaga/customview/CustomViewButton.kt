package com.mamisiaga.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.mamisiaga.R

class CustomViewButtonForm : androidx.appcompat.widget.AppCompatButton {
    private var enabledBackground: Drawable? = null
    private var disabledBackground: Drawable? = null
    private var textColorObject: Int = 0

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttribute: Int) : super(
        context,
        attributeSet,
        defStyleAttribute
    ) {
        init()
    }

    // Customize the button when there is a change
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // Change the button's background color
        background = when {
            isEnabled -> enabledBackground
            else -> disabledBackground
        }
    }

    private fun init() {
        textColorObject = ContextCompat.getColor(context, android.R.color.background_light)

        enabledBackground = ContextCompat.getDrawable(context, R.drawable.button_blue)
        disabledBackground = ContextCompat.getDrawable(context, R.drawable.button_light_blue)
    }
}