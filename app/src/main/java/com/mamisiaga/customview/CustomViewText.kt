package com.mamisiaga.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import com.mamisiaga.R
import com.mamisiaga.tools.isEmailFormat

class CustomEditTextEmail : AppCompatEditText, View.OnTouchListener {
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attribute: AttributeSet) : super(context, attribute) {
        init()
    }

    constructor(context: Context, attribute: AttributeSet, defStyleAttr: Int) : super(
        context,
        attribute,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                error =
                    if (!isEmailFormat(s.toString())) resources.getString(R.string.incorrect_email_format) else null
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean = false
}